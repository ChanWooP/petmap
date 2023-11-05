package com.cwpark.petmap.petmap.config.jpa;

import com.cwpark.petmap.petmap.config.security.UserDetailsImpl;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class LoginUserAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = null;
    String userId = null;

/*    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes()).getRequest();*/

    if(authentication == null || !authentication.isAuthenticated()) {
      return Optional.of("system");
    }

    // 익명사용자의 경우 타입이 String으로 들어오기 때문에 조건 지정
    if(authentication.getPrincipal() instanceof String) {
      userId = "system";
    } else {
      userDetails = (UserDetailsImpl) authentication.getPrincipal();
      userId = userDetails.getUser().getUserId();
    }

    return Optional.of(userId);
  }
}
