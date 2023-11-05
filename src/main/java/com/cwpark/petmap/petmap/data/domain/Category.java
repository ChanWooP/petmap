package com.cwpark.petmap.petmap.data.domain;

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
@Table(name = "MST_CATEGORY")
public class Category extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CATEGORY_ID")
    private Long categoryId;

    @Column(name="CATEGORY_PARENT")
    private Long categoryParent;

    @Column(name="CATEGORY_DEPTH")
    private int categoryDepth;

    @Column(name="CATEGORY_NAME")
    private String categoryName;

    @Column(name="CATEGORY_IMG")
    private String categoryImg;
}
