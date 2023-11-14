package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
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

        schema.sizeof(2);
        assertFalse(schema.isValid(data));
        data.put("secondKey", 8);
        assertTrue(schema.isValid(data));
        data.put("thirdKey", "value");
        assertFalse(schema.isValid(data));

        // Add new check to the list of checks, but it's not replacing previous sizeOf(2)
        schema.sizeof(3);
        assertFalse(schema.isValid(data));
    }

    @Test
    public void mapSchemaShapeTest() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());
        schemas.put("child", v.map().sizeof(2));
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Adam");
        human1.put("age", 100);
        assertTrue(schema.isValid(human1));
        human1.put("child", Map.of("name", "Kain", "age", 5));
        assertTrue(schema.isValid(human1));
        human1.put("child", Map.of("name", "Abel"));
        assertFalse(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Eva");
        human2.put("age", null);
        assertTrue(schema.isValid(human2));
        human2.put("fruit", "apple");
        assertTrue(schema.isValid(human2));

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertFalse(schema.isValid(human3));

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Adam");
        human4.put("hands", List.of("right", "left"));
        assertTrue(schema.isValid(human4));
        human4.put("age", -100);
        assertFalse(schema.isValid(human4));
    }
}
