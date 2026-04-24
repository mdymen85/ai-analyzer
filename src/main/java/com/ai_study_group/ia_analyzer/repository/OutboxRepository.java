package com.ai_study_group.ia_analyzer.repository;

import com.ai_study_group.ia_analyzer.entity.Outbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {

	@Query("SELECT o FROM Outbox o WHERE o.integrated = false ORDER BY o.createdAt ASC")
	List<Outbox> findUnprocessedOutboxRecords(Pageable pageable);
}
