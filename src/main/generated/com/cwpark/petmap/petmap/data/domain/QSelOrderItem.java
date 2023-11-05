package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSelOrderItem is a Querydsl query type for SelOrderItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSelOrderItem extends EntityPathBase<SelOrderItem> {

    private static final long serialVersionUID = 181374928L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSelOrderItem selOrderItem = new QSelOrderItem("selOrderItem");

    public final QBase _super = new QBase(this);

    public final QItem item;

    public final StringPath itemReviewYn = createString("itemReviewYn");

    public final NumberPath<Integer> ordAmt = createNumber("ordAmt", Integer.class);

    public final NumberPath<Integer> ordCnt = createNumber("ordCnt", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    public final QSelOrder selOrder;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public QSelOrderItem(String variable) {
        this(SelOrderItem.class, forVariable(variable), INITS);
    }

    public QSelOrderItem(Path<? extends SelOrderItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSelOrderItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSelOrderItem(PathMetadata metadata, PathInits inits) {
        this(SelOrderItem.class, metadata, inits);
    }

    public QSelOrderItem(Class<? extends SelOrderItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.selOrder = inits.isInitialized("selOrder") ? new QSelOrder(forProperty("selOrder"), inits.get("selOrder")) : null;
    }

}

