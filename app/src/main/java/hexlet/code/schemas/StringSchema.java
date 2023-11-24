package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {
    public StringSchema() {
        checks.put("required", input -> input instanceof String && !((String) input).isEmpty());
    }

    public StringSchema required() {
        requiredFlag = true;
        return this;
    }

    public StringSchema minLength(int length) {
        checks.put("minLength", input -> input.toString().length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        checks.put("contains", input -> input.toString().contains(substring));
        return this;
    }
}
