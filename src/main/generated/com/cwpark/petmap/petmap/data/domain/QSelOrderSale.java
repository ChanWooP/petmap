package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSelOrderSale is a Querydsl query type for SelOrderSale
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSelOrderSale extends EntityPathBase<SelOrderSale> {

    private static final long serialVersionUID = 181654788L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSelOrderSale selOrderSale = new QSelOrderSale("selOrderSale");

    public final QBase _super = new QBase(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    public final NumberPath<Integer> saleAmt = createNumber("saleAmt", Integer.class);

    public final NumberPath<Integer> saleCnt = createNumber("saleCnt", Integer.class);

    public final QSelOrder selOrder;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public QSelOrderSale(String variable) {
        this(SelOrderSale.class, forVariable(variable), INITS);
    }

    public QSelOrderSale(Path<? extends SelOrderSale> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSelOrderSale(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSelOrderSale(PathMetadata metadata, PathInits inits) {
        this(SelOrderSale.class, metadata, inits);
    }

    public QSelOrderSale(Class<? extends SelOrderSale> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.selOrder = inits.isInitialized("selOrder") ? new QSelOrder(forProperty("selOrder"), inits.get("selOrder")) : null;
    }

}

