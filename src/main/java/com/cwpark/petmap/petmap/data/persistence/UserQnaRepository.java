package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.classid.UserPointHisClassId;
import com.cwpark.petmap.petmap.data.classid.UserQnaClassId;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserPointHis;
import com.cwpark.petmap.petmap.data.domain.UserQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserQnaRepository extends JpaRepository<UserQna, UserQnaClassId> {

    @Query(value="SELECT IFNULL(MAX(u.qna_id)+1, 1) FROM user_qna u WHERE u.qna_user_id = :user", nativeQuery = true)
    int getQnaId(@Param("user") String user);

    Page<UserQna> findByUser(User user, Pageable pageable);

}
