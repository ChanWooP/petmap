package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.classid.SelOrderItemClassId;
import com.cwpark.petmap.petmap.data.classid.UserCartClassId;
import com.cwpark.petmap.petmap.data.domain.SelOrder;
import com.cwpark.petmap.petmap.data.domain.SelOrderItem;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserCart;
import com.cwpark.petmap.petmap.data.dto.SelOrderItemGroupInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCartRepository extends JpaRepository<UserCart, UserCartClassId> {
    List<UserCart> findByUserOrderByItem(User user);
}
