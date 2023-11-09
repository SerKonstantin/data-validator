package hexlet.code;

import hexlet.code.schemas.StringSchema;

public class App {
    public static void main(String[] args) {
        Validator v = new Validator();
        StringSchema schema = v.string().required().minLength(5).contains("hex");

        System.out.println(schema.isValid(null));
        System.out.println(schema.isValid(""));
        System.out.println(schema.isValid("hexa"));
        System.out.println(schema.isValid("huehuehue"));
        System.out.println(schema.isValid("hexlet"));

        StringSchema schema2 = v.string();
        System.out.println(schema2.isValid(null));
    }
}

