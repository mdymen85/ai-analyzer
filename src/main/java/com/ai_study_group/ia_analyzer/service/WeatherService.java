package com.ai_study_group.ia_analyzer.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Tool(description = "Get the current weather for a location")
    public String getCurrentWeather(String location) {
        // Your logic to call a real weather API
        return "The weather in " + location + " is sunny, 25°C.";
    }

    @Tool(description = "Get the 5-day forecast for a location")
    public String getForecast(String location) {
        return "Rain expected on Tuesday in " + location;
    }
}
