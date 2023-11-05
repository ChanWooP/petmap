package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"이메일 인증 API"})
@RestController
@RequestMapping("/api")
public class EmailRestController {

  private final EmailService emailService;

  @Autowired
  public EmailRestController(EmailService emailService) {
    this.emailService = emailService;
  }

  @GetMapping("v1/email-check/{userEmail}")
  public ResponseEntity<ApiResponse<Object>> emailConfirm(@ApiParam(value = "사용자 이메일") @PathVariable("userEmail") String userEmail) throws Exception {

    String result = emailService.sendSimpleMessage(userEmail);

    ApiResponse ar = ApiResponse.builder()
            .result(result)
            .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
            .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
            .build();

    return new ResponseEntity<>(ar, HttpStatus.OK);
  }

}
