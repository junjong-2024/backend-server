package com.debait.debait.config;

import com.debait.debait.rule.dto.request.RuleDetailDTO;
import com.debait.debait.rule.dto.request.RuleDetailDTOSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public SimpleModule customSerializerModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(RuleDetailDTO.class, new RuleDetailDTOSerializer());
        // 다른 직렬화기가 필요하면 추가할 수 있음
        return module;
    }
}
