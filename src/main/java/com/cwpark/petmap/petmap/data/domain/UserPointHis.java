package com.cwpark.petmap.petmap.data.domain;

import com.cwpark.petmap.petmap.data.classid.UserPointHisClassId;
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
@Table(name = "USER_POINT_HIS")
@IdClass(UserPointHisClassId.class)
public class UserPointHis extends Base {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="POINT_USER_ID")
    private User user;

    @Id
    @NotNull
    @Column(name="POINT_ID")
    private int pointId;

    @NotNull
    @Column(name="POINT_NUM")
    private int pointNum;

    @NotNull
    @Column(name="POINT_REASON")
    private String pointReason;

    @NotNull
    @Column(name="POINT_EXPLN")
    private String pointExpln;
}
