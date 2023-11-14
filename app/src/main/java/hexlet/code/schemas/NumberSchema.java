package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public final class NumberSchema extends BaseSchema {
    // Section to populate checkMethods (map located in superclass)
    {
        checkMethods.put("required", this::checkRequired);
        checkMethods.put("positive", this::checkPositive);
        checkMethods.put("range", this::checkRange);
    }

    // Section with methods to populate checking requirements (map located in superclass)
    public NumberSchema required() {
        requirements.computeIfAbsent("required", value -> new ArrayList<>()).add(true);
        return this;
    }

    public NumberSchema positive() {
        requirements.computeIfAbsent("positive", value -> new ArrayList<>()).add(true);
        return this;
    }

    public NumberSchema range(int start, int end) {
        // Make sure to have range look like "start <= input <= end"
        if (start > end) {
            int tmp = start;
            start = end;
            end = tmp;
        }

        int[] range = {start, end};
        requirements.computeIfAbsent("range", value -> new ArrayList<>()).add(range);
        return this;
    }

    // Section with methods to check each condition from requirements
    protected boolean isValidInput(Object input) {
        return input == null || input instanceof Integer;
    }

    private boolean checkRequired(Object parameters, Object input) {
        return !(input == null);
    }

    private boolean checkPositive(Object parameters, Object input) {
        if (input == null) {
            return true;
        }
        return (int) input > 0;
    }

    private boolean checkRange(Object listOfRanges, Object input) {
        if (input == null) {
            return true;
        }

        int value = (int) input;
        List<?> ranges = (List<?>) listOfRanges;

        for (Object range : ranges) {
            int start = ((int[]) range)[0];
            int end = ((int[]) range)[1];
            if (value < start || value > end) {
                return false;
            }
        }
        return true;
    }
}
