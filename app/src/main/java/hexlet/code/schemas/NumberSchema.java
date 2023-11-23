package hexlet.code.schemas;

import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema {
    protected boolean isValidType(Object input) {
        return input instanceof Integer;
    }

    public NumberSchema required() {
        requiredFlag = true;
        return this;
    }

    public NumberSchema positive() {
        checks.add(number -> (int) number > 0);
        return this;
    }

    public NumberSchema range(int start, int end) {
        // Make sure to have range look like "start <= input <= end"
        int normalizedStart = Math.min(start, end);
        int normalizedEnd = Math.max(start, end);
        Predicate<Object> checkRange = number -> (int) number >= normalizedStart && (int) number <= normalizedEnd;
        checks.add(checkRange);
        return this;
    }
}
