package com.ai_study_group.ia_analyzer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PropertyReader {

	private final Environment environment;

	/**
	 * Reads all properties from application.yml and returns as a Map<String, String>.
	 * Filters out sensitive properties like passwords and API keys.
	 *
	 * @return Map containing all non-sensitive properties
	 */
	public Map<String, String> getAllProperties() {
		Map<String, String> propertiesMap = new LinkedHashMap<>();
		Set<String> sensitiveKeys = getSensitiveKeys();

		try {
			// Get all property names from environment property sources
			if (environment instanceof AbstractEnvironment) {
				AbstractEnvironment env = (AbstractEnvironment) environment;
				env.getPropertySources().stream()
					.filter(ps -> ps instanceof EnumerablePropertySource)
					.map(ps -> (EnumerablePropertySource<?>) ps)
					.flatMap(ps -> Arrays.stream(ps.getPropertyNames()))
					.distinct()
					.sorted()
					.forEach(key -> {
						// Skip sensitive properties
						if (!isSensitiveKey(key, sensitiveKeys)) {
							String value = environment.getProperty(key);
							if (value != null) {
								propertiesMap.put(key, value);
							}
						}
					});

				log.info("Successfully loaded {} properties from application.yml", propertiesMap.size());
			}
		} catch (Exception e) {
			log.error("Error reading properties from application.yml", e);
		}

		return Collections.unmodifiableMap(propertiesMap);
	}

	/**
	 * Reads a specific property by key.
	 *
	 * @param key the property key
	 * @return the property value or null if not found
	 */
	public String getProperty(String key) {
		return environment.getProperty(key);
	}

	/**
	 * Reads a specific property by key with a default value.
	 *
	 * @param key the property key
	 * @param defaultValue the default value if property not found
	 * @return the property value or default value
	 */
	public String getProperty(String key, String defaultValue) {
		return environment.getProperty(key, defaultValue);
	}

	/**
	 * Checks if a property key is sensitive (should be filtered).
	 *
	 * @param key the property key
	 * @param sensitiveKeys set of sensitive key patterns
	 * @return true if the key is sensitive, false otherwise
	 */
	private boolean isSensitiveKey(String key, Set<String> sensitiveKeys) {
		return sensitiveKeys.stream()
			.anyMatch(sensitiveKey -> key.toLowerCase().contains(sensitiveKey.toLowerCase()));
	}

	/**
	 * Gets the set of sensitive property key patterns to exclude.
	 *
	 * @return set of sensitive key patterns
	 */
	private Set<String> getSensitiveKeys() {
		return new HashSet<>(Arrays.asList(
			"password",
			"api-key",
			"api_key",
			"secret",
			"token",
			"apikey",
			"credentials",
			"auth",
			"jwt",
			"oauth"
		));
	}
}

