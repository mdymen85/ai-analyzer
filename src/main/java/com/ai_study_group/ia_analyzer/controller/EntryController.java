package com.ai_study_group.ia_analyzer.controller;

import com.ai_study_group.ia_analyzer.dto.EntryDTO;
import com.ai_study_group.ia_analyzer.entity.Entry;
import com.ai_study_group.ia_analyzer.service.EntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/entries")
@RequiredArgsConstructor
public class EntryController {

	private final EntryService entryService;

	@PostMapping
	public ResponseEntity<Entry> createEntry(@RequestBody EntryDTO entryDTO) {
		log.info("Received request to create entry: {}", entryDTO);
		Entry createdEntry = entryService.createEntry(entryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdEntry);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Entry> getEntry(@PathVariable Long id) {
		log.info("Received request to get entry with id: {}", id);
		Entry entry = entryService.getEntry(id);
		return ResponseEntity.ok(entry);
	}
}

