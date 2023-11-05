package com.cwpark.petmap.petmap.data.dto;

import com.cwpark.petmap.petmap.data.enums.OAuthType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

  @NotBlank(message = "사용자 아이디는 필수 입력 항목입니다.")
  @Size(min = 1, max = 20, message = "아이디는 한 글자 이상 20자 미만으로 입력해야 합니다.")
  private String userId;

  private String userPassWord;

  private String userName;

  private String userBirth;

  private String userSex;

  private String userEmail;

  private String userPhone;

  private String userZipcode;

  private String userAddress1;

  private String userAddress2;

  private String userRole;

  private int userLoginFailCnt;

  private OAuthType oAuthType;

  private String userBizNo;

  private String userBizName;

  private int userPoint;
}
