package com.cwpark.petmap.petmap.data.classid;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockClassId implements Serializable {

    private ItemClassId item;
    private String stockDt;
    private Long stockId;
}
