package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.domain.Category;
import com.cwpark.petmap.petmap.data.domain.Notifi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotifiRepository extends JpaRepository<Notifi, Long> {
    @Query("SELECT n FROM Notifi n " +
            "WHERE n.notifiType = :type " +
            "  AND :now BETWEEN n.notifiStartDt AND n.notifiEndDt " +
            "ORDER BY notifiId "
    )
    List<Notifi> getNotifiEvent(String type, String now);
}
