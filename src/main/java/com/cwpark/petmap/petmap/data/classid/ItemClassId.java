package com.cwpark.petmap.petmap.data.classid;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemClassId implements Serializable {
    private String user;
    private String selItemId;
}
