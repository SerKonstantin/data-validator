package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema {
    protected boolean isValidType(Object input) {
        return input instanceof String;
    }

    public StringSchema required() {
        requiredFlag = true;
        return this;
    }

    public StringSchema minLength(int length) {
        Predicate<Object> checkMinLength = input -> input.toString().length() >= length;
        checks.add(checkMinLength);
        return this;
    }

    public StringSchema contains(String substring) {
        Predicate<Object> checkContains = input -> input.toString().contains(substring);
        checks.add(checkContains);
        return this;
    }
}
