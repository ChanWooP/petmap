package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findByUser(User user);

    Long countByUser(User user);
}
