package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserItemQna is a Querydsl query type for UserItemQna
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserItemQna extends EntityPathBase<UserItemQna> {

    private static final long serialVersionUID = 1732446941L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserItemQna userItemQna = new QUserItemQna("userItemQna");

    public final QBase _super = new QBase(this);

    public final QItem item;

    public final StringPath itemQnaAnswer = createString("itemQnaAnswer");

    public final StringPath itemQnaAnswerYn = createString("itemQnaAnswerYn");

    public final NumberPath<Long> itemQnaId = createNumber("itemQnaId", Long.class);

    public final StringPath itemQnaQuestion = createString("itemQnaQuestion");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public final QUser user;

    public QUserItemQna(String variable) {
        this(UserItemQna.class, forVariable(variable), INITS);
    }

    public QUserItemQna(Path<? extends UserItemQna> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserItemQna(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserItemQna(PathMetadata metadata, PathInits inits) {
        this(UserItemQna.class, metadata, inits);
    }

    public QUserItemQna(Class<? extends UserItemQna> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

