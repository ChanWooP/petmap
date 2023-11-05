package com.cwpark.petmap.petmap.config.security;

import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SuccessHandler(UserRepository userRepository, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    private RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    @Value("${server.servlet.session.timeout}")
    private Integer sessionTimeOut;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        Object principal = authentication.getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl)principal;
        User user = userDetails.getUser();

        // 기본 url 지정
        setDefaultTargetUrl("/");

        // 세션 타임아웃 지정
        request.getSession().setMaxInactiveInterval(sessionTimeOut);

        if(user.getUserLoginFailCnt() != 0) {
            user.setUserLoginFailCnt(0);
            userRepository.save(user);
        }

        // 캐시를 가져와서 비어있는지 확인
        if(savedRequest != null) {
            // 인증 받기 전 url로 이동하기
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request,response,targetUrl);
        } else {
            // 기본 url로 가도록 함
            redirectStrategy.sendRedirect(request,response,getDefaultTargetUrl());
        }
    }
}
