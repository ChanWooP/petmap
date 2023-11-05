package com.cwpark.petmap.petmap.data.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Base {

  @CreatedBy
  @Column(updatable = false, name = "REG_USER_ID")
  private String regUserId;

  @CreatedDate
  @Column(updatable = false, name = "REG_DATE")
  private LocalDateTime regDate;

  @LastModifiedBy
  @Column(name = "UPD_USER_ID")
  private String updUserId;

  @LastModifiedDate
  @Column(name = "UPD_DATE")
  private LocalDateTime updDate;
}