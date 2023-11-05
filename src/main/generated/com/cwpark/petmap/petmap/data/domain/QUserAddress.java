package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAddress is a Querydsl query type for UserAddress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAddress extends EntityPathBase<UserAddress> {

    private static final long serialVersionUID = -1531436800L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAddress userAddress = new QUserAddress("userAddress");

    public final QBase _super = new QBase(this);

    public final NumberPath<Long> addressId = createNumber("addressId", Long.class);

    public final StringPath addressUserAddress1 = createString("addressUserAddress1");

    public final StringPath addressUserAddress2 = createString("addressUserAddress2");

    public final StringPath addressUserName = createString("addressUserName");

    public final StringPath addressUserPhone = createString("addressUserPhone");

    public final StringPath addressUserZipcode = createString("addressUserZipcode");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public final QUser user;

    public QUserAddress(String variable) {
        this(UserAddress.class, forVariable(variable), INITS);
    }

    public QUserAddress(Path<? extends UserAddress> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAddress(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAddress(PathMetadata metadata, PathInits inits) {
        this(UserAddress.class, metadata, inits);
    }

    public QUserAddress(Class<? extends UserAddress> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

