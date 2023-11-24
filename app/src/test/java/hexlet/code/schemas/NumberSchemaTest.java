package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class NumberSchemaTest {
    @Test
    public void numberSchemaTest() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(null));

        schema.positive();
        assertFalse(schema.isValid(0));
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(-10));
        assertTrue(schema.isValid(10));

        schema.required();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid("10"));

        schema.range(6, 9);
        assertTrue(schema.isValid(6));
        assertTrue(schema.isValid(9));
        assertFalse(schema.isValid(5));
        assertFalse(schema.isValid(10));
    }
}
