package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserOrder is a Querydsl query type for UserOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserOrder extends EntityPathBase<UserOrder> {

    private static final long serialVersionUID = -341319846L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserOrder userOrder = new QUserOrder("userOrder");

    public final QBase _super = new QBase(this);

    public final StringPath ordAddress = createString("ordAddress");

    public final NumberPath<Integer> ordCouDc = createNumber("ordCouDc", Integer.class);

    public final NumberPath<Integer> ordDelAmt = createNumber("ordDelAmt", Integer.class);

    public final StringPath ordDt = createString("ordDt");

    public final NumberPath<Long> ordId = createNumber("ordId", Long.class);

    public final StringPath ordName = createString("ordName");

    public final NumberPath<Integer> ordNetAmt = createNumber("ordNetAmt", Integer.class);

    public final StringPath ordPhone = createString("ordPhone");

    public final NumberPath<Integer> ordPoiDc = createNumber("ordPoiDc", Integer.class);

    public final NumberPath<Integer> ordTotAmt = createNumber("ordTotAmt", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public final QUser user;

    public QUserOrder(String variable) {
        this(UserOrder.class, forVariable(variable), INITS);
    }

    public QUserOrder(Path<? extends UserOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserOrder(PathMetadata metadata, PathInits inits) {
        this(UserOrder.class, metadata, inits);
    }

    public QUserOrder(Class<? extends UserOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

