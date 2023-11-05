package com.cwpark.petmap.petmap.config.security;

import com.cwpark.petmap.petmap.data.domain.User;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*
내일 할 일
일단은 유저 객체 만들기
 */

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;
  private User user;

  @Autowired
  public UserDetailsImpl(User user) {
    this.user = user;
  }

  // 계정이 가지고 있는 권한 목록 저장하여 반환
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> roleList = new ArrayList<>();

    // 권한 목록 설정
    roleList.add(() -> {
      return "ROLE_" + user.getUserRole();
    });

    return roleList;
  }

  // 비밀번호 반환
  @Override
  public String getPassword() {
    return user.getUserPassWord();
  }

  // 아이디 반환
  @Override
  public String getUsername() {
    return user.getUserId();
  }

  // 계정 잠김 확인
  @Override
  public boolean isAccountNonLocked() {
    int userLoginFailCnt = user.getUserLoginFailCnt();

    if(userLoginFailCnt >= 5) {
      return false;
    } else {
      return true;
    }

  }

  // 계정 만료 확인
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  // 비밀번호 만료 확인
  @Override
  public boolean isCredentialsNonExpired() { return true; }

  // 계정 활성화 확인
  @Override
  public boolean isEnabled() {
    return true;
  }

}
