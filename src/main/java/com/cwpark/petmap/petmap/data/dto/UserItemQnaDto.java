package com.cwpark.petmap.petmap.data.dto;

import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserItemQnaDto {

  private String store;

  private String item;

  private String user;

  private Long itemQnaId;

  private String itemQnaQuestion;

  private String itemQnaAnswer;

  private String itemQnaAnswerYn;
}
