package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserCart is a Querydsl query type for UserCart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCart extends EntityPathBase<UserCart> {

    private static final long serialVersionUID = 1374089620L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserCart userCart = new QUserCart("userCart");

    public final QBase _super = new QBase(this);

    public final NumberPath<Integer> cartCnt = createNumber("cartCnt", Integer.class);

    public final QItem item;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public final QUser user;

    public QUserCart(String variable) {
        this(UserCart.class, forVariable(variable), INITS);
    }

    public QUserCart(Path<? extends UserCart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserCart(PathMetadata metadata, PathInits inits) {
        this(UserCart.class, metadata, inits);
    }

    public QUserCart(Class<? extends UserCart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

