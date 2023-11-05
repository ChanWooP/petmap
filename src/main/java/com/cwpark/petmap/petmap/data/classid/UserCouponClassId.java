package com.cwpark.petmap.petmap.data.classid;

import com.cwpark.petmap.petmap.data.domain.Coupon;
import com.cwpark.petmap.petmap.data.domain.User;
import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCouponClassId implements Serializable {
    private String user;
    private String coupon;
}
