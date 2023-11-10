package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

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
        schema.range(4, 9);

        assertTrue(schema.isValid(4));
        assertTrue(schema.isValid(9));
        assertFalse(schema.isValid(5));
        assertFalse(schema.isValid(10));
    }

}
