package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.domain.RememberMeToken;
import com.cwpark.petmap.petmap.data.persistence.RememberMeTokenRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RememberMeTokenService implements PersistentTokenRepository {

  private final RememberMeTokenRepository rememberMeTokenRepository;

  @Autowired
  public RememberMeTokenService (RememberMeTokenRepository rememberMeTokenRepository) {
    this.rememberMeTokenRepository = rememberMeTokenRepository;
  }

  @Override
  public void createNewToken(PersistentRememberMeToken token) {
    RememberMeToken newToken = new RememberMeToken(token);
    rememberMeTokenRepository.save(newToken);
  }

  @Override
  public void updateToken(String series, String tokenValue, Date lastUsed) {
    Optional<RememberMeToken> optional = this.rememberMeTokenRepository.findById(series);
    RememberMeToken token = null;

    if(optional.isPresent()) {
      token = optional.get();
    } else {
      token = optional.orElse(null);
    }

    if(token != null) {
      token.setLoginToken(tokenValue);
      token.setLoginTokenLastUsed(lastUsed);
      rememberMeTokenRepository.save(token);
    }
  }

  @Override
  public PersistentRememberMeToken getTokenForSeries(String seriesId) {
    Optional<RememberMeToken> optional = this.rememberMeTokenRepository.findById(seriesId);
    RememberMeToken token = null;

    if(optional.isPresent()) {
      token = optional.get();
    } else {
      token = optional.orElse(null);
    }

    return new PersistentRememberMeToken(token.getLoginTokenUserName(), token.getLoginTokenSeries(), token.getLoginToken(), token.getLoginTokenLastUsed());
  }

  @Override
  public void removeUserTokens(String username) {
    List<RememberMeToken> tokens = rememberMeTokenRepository.findByLoginTokenUserName(username);

    rememberMeTokenRepository.deleteAll(tokens);
  }
}
