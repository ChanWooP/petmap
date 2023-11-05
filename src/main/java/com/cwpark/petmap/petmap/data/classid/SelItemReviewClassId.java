package com.cwpark.petmap.petmap.data.classid;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelItemReviewClassId implements Serializable {
    private ItemClassId item;
    private String reviewDt;
    private Long reviewId;
}
