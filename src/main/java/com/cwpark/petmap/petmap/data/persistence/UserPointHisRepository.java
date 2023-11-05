package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.classid.UserPointHisClassId;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserPointHis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface UserPointHisRepository extends JpaRepository<UserPointHis, UserPointHisClassId> {

    @Query(value="SELECT IFNULL(MAX(u.point_id)+1, 1) FROM user_point_his u WHERE u.point_user_id = :user", nativeQuery = true)
    int getPointId(@Param("user") String user);

    Page<UserPointHis> findByUser(User user, Pageable pageable);

}
