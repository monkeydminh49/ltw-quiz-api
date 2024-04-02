package com.minhdunk.research.repository;

import com.minhdunk.research.entity.TestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {

    @Query("SELECT th FROM TestHistory th WHERE th.test.id = ?1")
    Optional<TestHistory> findByTestId(Long testId);
}
