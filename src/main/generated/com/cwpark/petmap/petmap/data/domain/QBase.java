package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBase is a Querydsl query type for Base
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBase extends EntityPathBase<Base> {

    private static final long serialVersionUID = 1017407322L;

    public static final QBase base = new QBase("base");

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final StringPath regUserId = createString("regUserId");

    public final DateTimePath<java.time.LocalDateTime> updDate = createDateTime("updDate", java.time.LocalDateTime.class);

    public final StringPath updUserId = createString("updUserId");

    public QBase(String variable) {
        super(Base.class, forVariable(variable));
    }

    public QBase(Path<? extends Base> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBase(PathMetadata metadata) {
        super(Base.class, metadata);
    }

}

