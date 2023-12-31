package com.cwpark.petmap.petmap.config.security;

import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User principal = userRepository.findById(username).orElseThrow(() -> {
      return new UsernameNotFoundException(username + " 사용자가 존재하지 않습니다.");
    });

    return new UserDetailsImpl(principal);
  }

}
