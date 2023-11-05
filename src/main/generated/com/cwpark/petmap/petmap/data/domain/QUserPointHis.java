package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserPointHis is a Querydsl query type for UserPointHis
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserPointHis extends EntityPathBase<UserPointHis> {

    private static final long serialVersionUID = 1454913622L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserPointHis userPointHis = new QUserPointHis("userPointHis");

    public final QBase _super = new QBase(this);

    public final StringPath pointExpln = createString("pointExpln");

    public final NumberPath<Integer> pointId = createNumber("pointId", Integer.class);

    public final NumberPath<Integer> pointNum = createNumber("pointNum", Integer.class);

    public final StringPath pointReason = createString("pointReason");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public final QUser user;

    public QUserPointHis(String variable) {
        this(UserPointHis.class, forVariable(variable), INITS);
    }

    public QUserPointHis(Path<? extends UserPointHis> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserPointHis(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserPointHis(PathMetadata metadata, PathInits inits) {
        this(UserPointHis.class, metadata, inits);
    }

    public QUserPointHis(Class<? extends UserPointHis> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

