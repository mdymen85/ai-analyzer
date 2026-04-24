package com.ai_study_group.ia_analyzer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PerformanceTools {

    private final PropertyReader propertyReader;

    @Tool(description = "Retrieves all non-sensitive application properties " +
            "from application.yml as a key-value map. Returns configuration for" +
            " database, Spring AI, outbox processing, record scheduler settings, " +
            "and system properties. Sensitive keys like passwords and API keys are " +
            "automatically filtered out for security.")
    public Map<String, String> getApplicationProperties() {
        return propertyReader.getAllProperties();
    }

}
