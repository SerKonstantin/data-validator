package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AppTest {
    @Test
    public void mainTest() {
        String expected = "Project start test";
        String actual = App.projectStart();
        assertEquals(expected, actual);
    }
}
