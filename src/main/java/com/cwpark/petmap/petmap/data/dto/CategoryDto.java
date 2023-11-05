package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

  private Long categoryId;

  private Long categoryParent;

  private int categoryDepth;

  private String categoryName;

  private String categoryImg;
}
