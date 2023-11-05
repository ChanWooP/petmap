package com.cwpark.petmap.petmap.data.classid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPointHisClassId implements Serializable {
    private String user;
    private int pointId;
}
