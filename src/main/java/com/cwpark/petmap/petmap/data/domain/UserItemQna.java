package com.cwpark.petmap.petmap.data.domain;

import com.cwpark.petmap.petmap.data.classid.UserItemQnaClassId;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate  // 변경된 필드만 적용
@DynamicInsert  // 같음
@Table(name = "USER_ITEM_QNA")
@IdClass(UserItemQnaClassId.class)
public class UserItemQna extends Base{
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "ITEM_QNA_STORE_ID", referencedColumnName = "SEL_USER_ID"),
            @JoinColumn(name = "ITEM_QNA_ITEM_ID", referencedColumnName = "SEL_ITEM_ID")
    })
    private Item item;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ITEM_QNA_USER_ID")
    private User user;

    @Id
    @Column(name="ITEM_QNA_ID")
    private Long itemQnaId;

    @Column(name="ITEM_QNA_QUESTION")
    private String itemQnaQuestion;

    @Column(name="ITEM_QNA_ANSWER")
    private String itemQnaAnswer;

    @Column(name="ITEM_QNA_ANSWER_YN")
    private String itemQnaAnswerYn;
}
