package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapSchemaTest {
    @Test
    public void mapSchemaTest() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertTrue(schema.isValid(null));

        schema.required();

        assertFalse(schema.isValid(null));
        Map<String, Object> data = new HashMap<>();
        assertTrue(schema.isValid(data));
        data.put("firstKey", Map.of("innerKey", 5));
        assertTrue(schema.isValid(data));

        schema.sizeOf(2);
        assertFalse(schema.isValid(data));
        data.put("secondKey", 8);
        assertTrue(schema.isValid(data));
        data.put("thirdKey", "value");
        assertFalse(schema.isValid(data));

        // Add new check to the list of checks, but it's not replacing previous sizeOf(2)
        schema.sizeOf(3);
        assertFalse(schema.isValid(data));
    }
}