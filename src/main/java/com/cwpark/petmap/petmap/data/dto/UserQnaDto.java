package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQnaDto {
    private UserDto userDto;

    private int qnaId;

    private String qnaQuestion;

    private String qnaAnswer;

    private String qnaAnswerYn;
}
