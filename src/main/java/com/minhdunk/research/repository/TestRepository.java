package com.minhdunk.research.repository;

import com.minhdunk.research.entity.Test;
import com.minhdunk.research.utils.TestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("SELECT t FROM Test t JOIN FETCH t.questions q WHERE t.id = :id")
    Optional<Test> findByIdFetchAll(Long id);
}
