package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ValidatorTest {
    @Test
    public void validatorStringTest() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertFalse(schema.isValid(5));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));

        schema.minLength(1);
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(""));

        schema.required();
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));

        assertTrue(schema.isValid("om"));
        schema.minLength(3);
        assertFalse(schema.isValid("om"));

        assertTrue(schema.isValid("some text"));
        assertTrue(schema.contains("om").isValid("some text"));
        assertFalse(schema.contains("omaha").isValid("some text"));

        assertFalse(schema.isValid("some text"));
    }

    @Test
    public void validatorNumberTest() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertFalse(schema.isValid("10"));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(null));

        schema.positive();
        assertFalse(schema.isValid(0));
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(-10));
        assertTrue(schema.isValid(10));

        schema.required();
        assertFalse(schema.isValid(null));

        schema.range(10, 5);
        schema.range(6, 9);
        assertTrue(schema.isValid(6));
        assertTrue(schema.isValid(9));
        assertFalse(schema.isValid(5));
        assertFalse(schema.isValid(10));
    }

    @Test
    public void validatorMapTest() {
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
