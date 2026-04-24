package com.ai_study_group.ia_analyzer.config;

import jakarta.annotation.Resource;
import org.springaicommunity.agent.tools.SkillsTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SkillConfig {

    @Resource


    @Bean
    public SkillsTool skillsTool() {
        return SkillsTool.builder().addSkillsResource("./agents/skills/");
    }

}