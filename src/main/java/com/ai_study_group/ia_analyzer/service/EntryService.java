package com.ai_study_group.ia_analyzer.service;

import com.ai_study_group.ia_analyzer.dto.EntryDTO;
import com.ai_study_group.ia_analyzer.entity.Entry;
import com.ai_study_group.ia_analyzer.entity.Outbox;
import com.ai_study_group.ia_analyzer.enums.OutboxStatus;
import com.ai_study_group.ia_analyzer.repository.EntryRepository;
import com.ai_study_group.ia_analyzer.repository.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntryService {

	private final EntryRepository entryRepository;
	private final OutboxRepository outboxRepository;
	private final ObjectMapper objectMapper;

	@Transactional
	public Entry createEntry(EntryDTO entryDTO) {
		try {
			// Convert DTO to Entry entity
			Entry entry = Entry.builder()
					.amount(entryDTO.getAmount())
					.operationType(entryDTO.getOperationType())
					.branch(entryDTO.getBranch())
					.account(entryDTO.getAccount())
					.build();

			// Save Entry to database
			Entry savedEntry = entryRepository.save(entry);
			log.info("Entry saved successfully with id: {}", savedEntry.getId());

			// Convert Entry to JSON and store in Outbox
			String entryJson = objectMapper.writeValueAsString(savedEntry);
			Outbox outbox = Outbox.builder()
					.body(entryJson)
					.integrated(false)
					.attempts(0)
					.status(OutboxStatus.NEW)
					.error(null)
					.createdAt(LocalDateTime.now())
					.updatedAt(LocalDateTime.now())
					.build();

			outboxRepository.save(outbox);
			log.info("Outbox record created successfully for entry id: {}", savedEntry.getId());

			return savedEntry;
		} catch (Exception e) {
			log.error("Error creating entry and outbox record", e);
			throw new RuntimeException("Error creating entry: " + e.getMessage(), e);
		}
	}

	public Entry getEntry(Long id) {
		return entryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Entry not found with id: " + id));
	}

	public List<Outbox> getUnprocessedOutboxRecords(int batchSize) {
		Pageable pageable = PageRequest.of(0, batchSize);
		return outboxRepository.findUnprocessedOutboxRecords(pageable);
	}
}
