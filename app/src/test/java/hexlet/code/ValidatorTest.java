package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValidatorTest {
    @Test
    public void validatorTest() {
        Validator v = new Validator();

        assertNotNull(v.string());
        assertNotNull(v.number());
        assertNotNull(v.map());
    }
}
