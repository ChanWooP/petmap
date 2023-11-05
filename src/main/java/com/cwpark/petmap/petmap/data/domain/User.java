package com.cwpark.petmap.petmap.data.domain;


import com.cwpark.petmap.petmap.data.enums.OAuthType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate  // 변경된 필드만 적용
@DynamicInsert  // 같음
@Table(name = "MST_USER")
public class User extends Base {

  @Id
  @NotBlank
  @Column(name = "USER_ID")
  private String userId;

  @Column(name = "USER_PASSWORD")
  private String userPassWord;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "USER_BIRTH")
  private String userBirth;

  @Column(name = "USER_SEX")
  private String userSex;

  @Column(name = "USER_EMAIL")
  private String userEmail;

  @Column(name = "USER_PHONE")
  private String userPhone;

  @Column(name = "USER_ZIPCODE")
  private String userZipcode;

  @Column(name = "USER_ADDRESS1")
  private String userAddress1;

  @Column(name = "USER_ADDRESS2")
  private String userAddress2;

  @Column(name = "USER_ROLE")
  private String userRole;

  @Column(name = "USER_LOGIN_FAIL_CNT")
  private int userLoginFailCnt;

  @Column(name = "USER_OAUTH_TYPE")
  @Enumerated(EnumType.STRING)
  private OAuthType oAuthType;

  @Column(name = "USER_BIZ_NO")
  private String userBizNo;

  @Column(name = "USER_BIZ_NAME")
  private String userBizName;

  @Column(name = "USER_POINT")
  private int userPoint;

}
