package com.cwpark.petmap.petmap.config.security;

import com.cwpark.petmap.petmap.service.RememberMeTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsServiceImpl userDetailsService;
  private final AuthenticationFailureHandler failureHandler;
  private final AuthenticationSuccessHandler successHandler;
  private final RememberMeTokenService rememberMeTokenService;

  @Autowired
  public SpringSecurityConfiguration(UserDetailsServiceImpl userDetailsService, AuthenticationFailureHandler failureHandler, AuthenticationSuccessHandler successHandler, RememberMeTokenService rememberMeTokenService) {
    this.userDetailsService = userDetailsService;
    this.failureHandler = failureHandler;
    this.successHandler = successHandler;
    this.rememberMeTokenService = rememberMeTokenService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
  
  // 시큐리티 인증 시 사용자 지정 계정으로만 로그인 가능하도록 설정
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  // 시큐리티 기본 설정
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 인증
    http
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/",
                "/files/notifi/**", "/files/category/**", "/files/item/**",
                "/search", "/notifi", "/buy",
                "/api/v1/item/**", "/api/v1/index", "/api/v1/notifi/**", "/api/v1/category/**", "/api/v1/buy/**", "/api/v1/useritemqna/list/**",
                "/api/v1/user/check/id/**","/api/v1/user/check/email/**", "/api/v1/user/email/**", "/api/v1/user/id/**",
                "/css/**", "/js/**", "/img/**", "/auth/**",
                "/api/**/user", "/api/**/user/check", "/api/**/email-check/**" , "/oauth/**").permitAll() // 해당 경로 인증 불필요
        .antMatchers("/admin/**", "/api/**/admin/**","/swagger*/**", "/v2/api-docs/**").hasRole("ADMIN") // ADMIN 인증 필요
        .antMatchers("/sel/**", "/api/**/sel/**").hasRole("SELLER")
        .anyRequest().authenticated() // 이 외 나머지 경로는 인증 필요
    ;

    // 로그인
    http
        .formLogin()
        .loginPage("/auth/login") // 사용자 정의 로그인 페이지
        .loginProcessingUrl("/auth/securitylogin")
        .failureHandler(failureHandler)
        .successHandler(successHandler)
    ;

    // 리멤버미
    http
        .rememberMe()
        .key("jpub")
        .rememberMeParameter("remember-me")
        .rememberMeCookieName("jpubcookie")
        .tokenValiditySeconds(86400 * 30) // 30일
        .tokenRepository(rememberMeTokenService)
        .userDetailsService(userDetailsService)
    ;

    // 로그아웃
    http
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/auth/login")// 로그아웃 성공 시 이동할 페이지
        .invalidateHttpSession(true) // 로그아웃 성공 시 세션 제거
        .clearAuthentication(true) // 로그아웃 성공 시 인증 정보 제거
    ;

    // 세션
    http.sessionManagement()
        .maximumSessions(1) // 세션 최대 허용 수
        .maxSessionsPreventsLogin(false) // false이면 중복 로그인 시 이전 로그인이 풀림
        .expiredUrl("/")
    ;

    // 인증 없음
    http
        .exceptionHandling()
        .accessDeniedPage("/auth/accessDenied");
  }
}
