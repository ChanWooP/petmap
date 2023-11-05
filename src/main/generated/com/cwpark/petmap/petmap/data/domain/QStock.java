package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStock is a Querydsl query type for Stock
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStock extends EntityPathBase<Stock> {

    private static final long serialVersionUID = 1491117997L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStock stock = new QStock("stock");

    public final QBase _super = new QBase(this);

    public final QItem item;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    public final StringPath stockDt = createString("stockDt");

    public final StringPath stockExpln = createString("stockExpln");

    public final NumberPath<Long> stockId = createNumber("stockId", Long.class);

    public final NumberPath<Integer> stockQty = createNumber("stockQty", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public QStock(String variable) {
        this(Stock.class, forVariable(variable), INITS);
    }

    public QStock(Path<? extends Stock> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStock(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStock(PathMetadata metadata, PathInits inits) {
        this(Stock.class, metadata, inits);
    }

    public QStock(Class<? extends Stock> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
    }

}

