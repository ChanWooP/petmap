package com.cwpark.petmap.petmap.data.classid;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCartClassId implements Serializable {
    private String user;
    private ItemClassId item;
}
