package com.ai_study_group.ia_analyzer.controller;

import com.ai_study_group.ia_analyzer.service.PropertyReader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ConfigController {

	private final PropertyReader propertyReader;

	/**
	 * Endpoint to retrieve all non-sensitive properties from application.yml.
	 * Useful for debugging and configuration verification.
	 *
	 * @return Map containing all non-sensitive properties
	 */
	@GetMapping("/properties")
	public Map<String, String> getAllProperties() {
		return propertyReader.getAllProperties();
	}
}

