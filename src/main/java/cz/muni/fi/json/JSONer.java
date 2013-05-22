package cz.muni.fi.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.StringWriter;

public class JSONer {
    
    private static ObjectMapper mapper = new ObjectMapper();
    private static JsonFactory jsonFactory = new JsonFactory();
    
    public static String getEventJson(String fqnNS, String eventType, String[] names, Object... values) {
        StringWriter writer = new StringWriter();
        try (JsonGenerator json = jsonFactory.createGenerator(writer)) {
            json.writeStartObject();
            
            json.writeStringField("schema", fqnNS.replace('.', '/') + ".json#/definitions/" + eventType);
            
            json.writeObjectFieldStart("properties");
            for (int i = 0; i < names.length; i++) {
                json.writeFieldName(names[i]);
                if (values[i] instanceof Number) {
                    if (((Number)values[i]).doubleValue() % 1 == 0) {
                        json.writeNumber(((Number)values[i]).longValue());
                    } else {
                        json.writeNumber(((Number)values[i]).doubleValue());
                    }
                } else {
                    if (values[i] instanceof Boolean) {
                        json.writeBoolean((boolean)values[i]);
                    } else {
                        json.writeString(values[i].toString());
                    }
                }
            }
            json.writeEndObject();
            
            json.writeEndObject();
        } catch (IOException e) {
        }
        
        return writer.toString();
    }

    public static String addEntityToEventJson(String entity, String json) {
        try {
            ObjectNode root = (ObjectNode) mapper.readTree(json);
            root.put("entity", entity);
            json = root.toString();
        } catch (IOException ex) {
        }
        
        return json;
    }
}
