package com.cwpark.petmap.petmap.data.domain;

import com.cwpark.petmap.petmap.data.classid.UserCartClassId;
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
@Table(name = "user_cart")
@IdClass(UserCartClassId.class)
public class UserCart extends Base{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CART_USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
              @JoinColumn(name="CART_STORE_ID", referencedColumnName = "SEL_USER_ID")
            , @JoinColumn(name="CART_ITEM_ID", referencedColumnName = "SEL_ITEM_ID")
    })
    private Item item;

    @Column(name = "CART_CNT")
    private int cartCnt;
}
