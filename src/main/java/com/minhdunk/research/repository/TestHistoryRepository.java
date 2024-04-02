package com.minhdunk.research.repository;

import com.minhdunk.research.entity.TestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {

}
