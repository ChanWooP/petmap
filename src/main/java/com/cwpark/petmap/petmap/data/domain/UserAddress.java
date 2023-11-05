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
@Table(name = "MST_USER_ADDRESS")
public class UserAddress extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ADDRESS_ID")
    private Long addressId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @Column(name = "ADDRESS_USER_PHONE")
    private String addressUserPhone;

    @Column(name = "ADDRESS_USER_ZIPCODE")
    private String addressUserZipcode;

    @Column(name = "ADDRESS_USER_ADDRESS1")
    private String addressUserAddress1;

    @Column(name = "ADDRESS_USER_ADDRESS2")
    private String addressUserAddress2;

    @Column(name = "ADDRESS_USER_NAME")
    private String addressUserName;
}
