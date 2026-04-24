package com.ai_study_group.ia_analyzer.repository;

import com.ai_study_group.ia_analyzer.entity.Entry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends CrudRepository<Entry, Long> {
}

