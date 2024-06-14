package com.debait.debait.rule.dto.request;

import com.debait.debait.rule.dto.request.RuleDetailDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class RuleDetailDTOSerializer extends JsonSerializer<RuleDetailDTO> {

    @Override
    public void serialize(RuleDetailDTO value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        // Serialize properties of RuleDetailDTO as needed
        gen.writeStringField("debater", value.getDebater());
        gen.writeStringField("msg", value.getMsg());
        gen.writeNumberField("time", value.getTime());
        // Add more fields as necessary
        gen.writeEndObject();
    }
}
