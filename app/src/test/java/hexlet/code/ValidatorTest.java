package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ValidatorTest {
    @Test
    public void validatorTest() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertFalse(schema.isValid(5));

        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));
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
}