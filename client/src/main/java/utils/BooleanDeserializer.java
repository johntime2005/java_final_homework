package utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

public class BooleanDeserializer extends JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (p.getCurrentToken() == JsonToken.START_OBJECT) {
            JsonNode node = p.readValueAsTree();
            if (node.has("isborrowed")) {
                String text = node.get("isborrowed").asText();
                return "1".equals(text) || "true".equalsIgnoreCase(text);
            }
            return null;
        } else {
            String text = p.getText();
            if ("1".equals(text) || "true".equalsIgnoreCase(text)) {
                return true;
            } else if ("0".equals(text) || "false".equalsIgnoreCase(text)) {
                return false;
            }
            return null;
        }
    }
}
