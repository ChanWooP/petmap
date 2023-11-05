package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.domain.RememberMeToken;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, String> {

  List<RememberMeToken> findByLoginTokenUserName(String userName);
}
