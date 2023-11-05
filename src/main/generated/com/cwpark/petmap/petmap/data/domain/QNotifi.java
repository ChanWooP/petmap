package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNotifi is a Querydsl query type for Notifi
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotifi extends EntityPathBase<Notifi> {

    private static final long serialVersionUID = -1167591038L;

    public static final QNotifi notifi = new QNotifi("notifi");

    public final QBase _super = new QBase(this);

    public final StringPath notifiBannerImg = createString("notifiBannerImg");

    public final StringPath notifiEndDt = createString("notifiEndDt");

    public final NumberPath<Long> notifiId = createNumber("notifiId", Long.class);

    public final StringPath notifiMainImg = createString("notifiMainImg");

    public final StringPath notifiStartDt = createString("notifiStartDt");

    public final StringPath notifiText = createString("notifiText");

    public final StringPath notifiTitle = createString("notifiTitle");

    public final StringPath notifiType = createString("notifiType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public QNotifi(String variable) {
        super(Notifi.class, forVariable(variable));
    }

    public QNotifi(Path<? extends Notifi> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotifi(PathMetadata metadata) {
        super(Notifi.class, metadata);
    }

}

