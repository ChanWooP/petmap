package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRememberMeToken is a Querydsl query type for RememberMeToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRememberMeToken extends EntityPathBase<RememberMeToken> {

    private static final long serialVersionUID = 908411851L;

    public static final QRememberMeToken rememberMeToken = new QRememberMeToken("rememberMeToken");

    public final QBase _super = new QBase(this);

    public final StringPath loginToken = createString("loginToken");

    public final DateTimePath<java.util.Date> loginTokenLastUsed = createDateTime("loginTokenLastUsed", java.util.Date.class);

    public final StringPath loginTokenSeries = createString("loginTokenSeries");

    public final StringPath loginTokenUserName = createString("loginTokenUserName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public QRememberMeToken(String variable) {
        super(RememberMeToken.class, forVariable(variable));
    }

    public QRememberMeToken(Path<? extends RememberMeToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRememberMeToken(PathMetadata metadata) {
        super(RememberMeToken.class, metadata);
    }

}

