package com.cwpark.petmap.petmap.data.classid;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelOrderSaleClassId implements Serializable {
    private SelOrderClassId selOrder;
}
