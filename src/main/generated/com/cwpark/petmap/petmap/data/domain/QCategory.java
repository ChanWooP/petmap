package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = 1691595015L;

    public static final QCategory category = new QCategory("category");

    public final QBase _super = new QBase(this);

    public final NumberPath<Integer> categoryDepth = createNumber("categoryDepth", Integer.class);

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final StringPath categoryImg = createString("categoryImg");

    public final StringPath categoryName = createString("categoryName");

    public final NumberPath<Long> categoryParent = createNumber("categoryParent", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

