package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1017990228L;

    public static final QUser user = new QUser("user");

    public final QBase _super = new QBase(this);

    public final EnumPath<com.cwpark.petmap.petmap.data.enums.OAuthType> oAuthType = createEnum("oAuthType", com.cwpark.petmap.petmap.data.enums.OAuthType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public final StringPath userAddress1 = createString("userAddress1");

    public final StringPath userAddress2 = createString("userAddress2");

    public final StringPath userBirth = createString("userBirth");

    public final StringPath userBizName = createString("userBizName");

    public final StringPath userBizNo = createString("userBizNo");

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userId = createString("userId");

    public final NumberPath<Integer> userLoginFailCnt = createNumber("userLoginFailCnt", Integer.class);

    public final StringPath userName = createString("userName");

    public final StringPath userPassWord = createString("userPassWord");

    public final StringPath userPhone = createString("userPhone");

    public final NumberPath<Integer> userPoint = createNumber("userPoint", Integer.class);

    public final StringPath userRole = createString("userRole");

    public final StringPath userSex = createString("userSex");

    public final StringPath userZipcode = createString("userZipcode");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

