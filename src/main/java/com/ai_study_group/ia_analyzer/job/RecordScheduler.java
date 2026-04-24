package com.ai_study_group.ia_analyzer.job;

import com.ai_study_group.ia_analyzer.entity.Outbox;
import com.ai_study_group.ia_analyzer.service.EntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecordScheduler {

	private final EntryService entryService;

	@Qualifier("asyncConfigTaskExecutor")
	private final Executor executor;

	@Value("${spring.outbox.batch-size:100}")
	private int batchSize;

	@Scheduled(fixedRateString = "${spring.recordScheduler.fixed-rate:5000}")
	public void recordData() {
		log.info("Scheduled task running every 5 minutes - Processing outbox records");

		try {
			List<Outbox> unprocessedRecords = entryService.getUnprocessedOutboxRecords(batchSize);
			log.info("Found {} unprocessed outbox records to process", unprocessedRecords.size());

			for (Outbox record : unprocessedRecords) {
				executor.execute(() -> {
					log.info("Thread : {}. Processing outbox record ID: {}, Body: {}", Thread.currentThread().getName(), record.getId(), record.getBody());
				});
			}

			log.info("Completed submitting outbox records for processing");
		} catch (Exception e) {
			log.error("Error processing outbox records", e);
		}
	}
}
