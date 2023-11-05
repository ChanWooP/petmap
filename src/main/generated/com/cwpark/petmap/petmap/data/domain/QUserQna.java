package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserQna is a Querydsl query type for UserQna
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQna extends EntityPathBase<UserQna> {

    private static final long serialVersionUID = 182886640L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserQna userQna = new QUserQna("userQna");

    public final QBase _super = new QBase(this);

    public final StringPath qnaAnswer = createString("qnaAnswer");

    public final StringPath qnaAnswerYn = createString("qnaAnswerYn");

    public final NumberPath<Integer> qnaId = createNumber("qnaId", Integer.class);

    public final StringPath qnaQuestion = createString("qnaQuestion");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public final QUser user;

    public QUserQna(String variable) {
        this(UserQna.class, forVariable(variable), INITS);
    }

    public QUserQna(Path<? extends UserQna> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserQna(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserQna(PathMetadata metadata, PathInits inits) {
        this(UserQna.class, metadata, inits);
    }

    public QUserQna(Class<? extends UserQna> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

