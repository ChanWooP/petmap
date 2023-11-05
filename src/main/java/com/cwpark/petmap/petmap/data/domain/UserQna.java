package com.cwpark.petmap.petmap.data.domain;

import com.cwpark.petmap.petmap.data.classid.UserQnaClassId;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate  // 변경된 필드만 적용
@DynamicInsert  // 같음
@Table(name = "USER_QNA")
@IdClass(UserQnaClassId.class)
public class UserQna extends Base {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QNA_USER_ID")
    private User user;

    @Id
    @NotNull
    @Column(name="QNA_ID")
    private int qnaId;

    @Column(name="QNA_QUESTION")
    private String qnaQuestion;

    @Column(name="QNA_ANSWER")
    private String qnaAnswer;

    @Column(name="QNA_ANSWER_YN")
    private String qnaAnswerYn;
}
