package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 1017633692L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final QBase _super = new QBase(this);

    public final QCategory category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    public final NumberPath<Integer> selDeilPrice = createNumber("selDeilPrice", Integer.class);

    public final StringPath selExpln = createString("selExpln");

    public final NumberPath<Integer> selHeartCount = createNumber("selHeartCount", Integer.class);

    public final StringPath selItemId = createString("selItemId");

    public final StringPath selItemName = createString("selItemName");

    public final NumberPath<Integer> selItemPrice = createNumber("selItemPrice", Integer.class);

    public final StringPath selMainImg = createString("selMainImg");

    public final StringPath selMiniImg = createString("selMiniImg");

    public final NumberPath<Integer> selSaleCount = createNumber("selSaleCount", Integer.class);

    public final NumberPath<Float> selStarPoint = createNumber("selStarPoint", Float.class);

    public final NumberPath<Double> selStarPointAvg = createNumber("selStarPointAvg", Double.class);

    public final NumberPath<Integer> selStarPointCnt = createNumber("selStarPointCnt", Integer.class);

    public final NumberPath<Integer> selStockCount = createNumber("selStockCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public final QUser user;

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

