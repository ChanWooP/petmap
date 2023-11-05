package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSelItemReview is a Querydsl query type for SelItemReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSelItemReview extends EntityPathBase<SelItemReview> {

    private static final long serialVersionUID = -1745478180L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSelItemReview selItemReview = new QSelItemReview("selItemReview");

    public final QBase _super = new QBase(this);

    public final QItem item;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    public final StringPath reviewDt = createString("reviewDt");

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final NumberPath<Integer> reviewStarPoint = createNumber("reviewStarPoint", Integer.class);

    public final StringPath reviewText = createString("reviewText");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public final QUser user;

    public QSelItemReview(String variable) {
        this(SelItemReview.class, forVariable(variable), INITS);
    }

    public QSelItemReview(Path<? extends SelItemReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSelItemReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSelItemReview(PathMetadata metadata, PathInits inits) {
        this(SelItemReview.class, metadata, inits);
    }

    public QSelItemReview(Class<? extends SelItemReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

