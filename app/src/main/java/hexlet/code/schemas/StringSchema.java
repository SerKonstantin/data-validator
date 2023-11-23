package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {
    protected boolean isValidType(Object input) {
        return input instanceof String;
    }

    public StringSchema required() {
        requiredFlag = true;
        return this;
    }

    public StringSchema minLength(int length) {
        checks.add(input -> input.toString().length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        checks.add(input -> input.toString().contains(substring));
        return this;
    }
}
