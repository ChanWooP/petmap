package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotifiDto {

  private Long notifiId;

  private String notifiType;

  private String notifiTitle;

  private String notifiText;

  private String notifiBannerImg;

  private String notifiMainImg;

  private String notifiStartDt;

  private String notifiEndDt;
}
