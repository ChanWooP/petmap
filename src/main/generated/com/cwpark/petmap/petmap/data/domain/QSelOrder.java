package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSelOrder is a Querydsl query type for SelOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSelOrder extends EntityPathBase<SelOrder> {

    private static final long serialVersionUID = -1482197219L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSelOrder selOrder = new QSelOrder("selOrder");

    public final QBase _super = new QBase(this);

    public final NumberPath<Integer> ordDelAmt = createNumber("ordDelAmt", Integer.class);

    public final StringPath ordDt = createString("ordDt");

    public final NumberPath<Long> ordId = createNumber("ordId", Long.class);

    public final StringPath ordInvoice = createString("ordInvoice");

    public final StringPath ordStatus = createString("ordStatus");

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

    public final QUserOrder userOrder;

    public QSelOrder(String variable) {
        this(SelOrder.class, forVariable(variable), INITS);
    }

    public QSelOrder(Path<? extends SelOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSelOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSelOrder(PathMetadata metadata, PathInits inits) {
        this(SelOrder.class, metadata, inits);
    }

    public QSelOrder(Class<? extends SelOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
        this.userOrder = inits.isInitialized("userOrder") ? new QUserOrder(forProperty("userOrder"), inits.get("userOrder")) : null;
    }

}

