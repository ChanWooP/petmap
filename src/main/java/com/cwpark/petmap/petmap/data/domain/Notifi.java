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
@Table(name = "MST_NOTIFI")
public class Notifi extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="NOTIFI_ID")
    private Long notifiId;

    @Column(name="NOTIFI_TYPE")
    private String notifiType;

    @Column(name="NOTIFI_TITLE")
    private String notifiTitle;

    @Column(name="NOTIFI_TEXT")
    private String notifiText;

    @Column(name="NOTIFI_BANNER_IMG")
    private String notifiBannerImg;

    @Column(name="NOTIFI_MAIN_IMG")
    private String notifiMainImg;

    @Column(name="NOTIFI_START_DT")
    private String notifiStartDt;

    @Column(name="NOTIFI_END_DT")
    private String notifiEndDt;

}
