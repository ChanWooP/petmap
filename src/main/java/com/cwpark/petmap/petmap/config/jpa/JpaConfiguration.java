package com.cwpark.petmap.petmap.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@Configuration
public class JpaConfiguration {
  @Bean
  public AuditorAware<String> auditorProvider() {
    return new LoginUserAuditorAware();
  }
}
