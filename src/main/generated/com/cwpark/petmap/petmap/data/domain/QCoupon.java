package com.cwpark.petmap.petmap.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCoupon is a Querydsl query type for Coupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoupon extends EntityPathBase<Coupon> {

    private static final long serialVersionUID = -1482474897L;

    public static final QCoupon coupon = new QCoupon("coupon");

    public final QBase _super = new QBase(this);

    public final NumberPath<Integer> couponAmt = createNumber("couponAmt", Integer.class);

    public final StringPath couponCode = createString("couponCode");

    public final StringPath couponEdate = createString("couponEdate");

    public final StringPath couponExpln = createString("couponExpln");

    public final StringPath couponName = createString("couponName");

    public final StringPath couponSdate = createString("couponSdate");

    public final StringPath couponType = createString("couponType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final StringPath regUserId = _super.regUserId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updDate = _super.updDate;

    //inherited
    public final StringPath updUserId = _super.updUserId;

    public QCoupon(String variable) {
        super(Coupon.class, forVariable(variable));
    }

    public QCoupon(Path<? extends Coupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCoupon(PathMetadata metadata) {
        super(Coupon.class, metadata);
    }

}

