package com.synchrony.springboot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.synchrony.springboot.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @Query(value = "select session, count(*) from submissions GROUP BY author order by count(author) desc", nativeQuery = true)
    List<Object[]> findByAuthorOccurance();
}