package com.cwpark.petmap.petmap.data.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate  // 변경된 필드만 적용
@DynamicInsert  // 같음
@Table(name = "MST_LOGIN_TOKEN")
public class RememberMeToken extends Base implements Serializable {
  @Id
  @Column(name = "LOGIN_TOKEN_SERIES")
  private String loginTokenSeries;

  @Column(name = "LOGIN_TOKEN_USERNAME")
  private String loginTokenUserName;

  @Column(name = "LOGIN_TOKEN")
  private String loginToken;

  @Column(name = "LOGIN_TOKEN_LAST_USED")
  private Date loginTokenLastUsed;

  public RememberMeToken(PersistentRememberMeToken token) {
    this.loginTokenSeries = token.getSeries();
    this.loginTokenUserName = token.getUsername();
    this.loginToken = token.getTokenValue();
    this.loginTokenLastUsed = token.getDate();
  }
}
