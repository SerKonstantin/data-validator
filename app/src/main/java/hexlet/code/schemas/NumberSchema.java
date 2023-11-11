package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class NumberSchema {
    private final Map<String, List<Object>> requirements = new HashMap<>();
    private final Map<String, BiPredicate<Object, Object>> checkMethods = Map.of(
            "required", this::checkRequired,
            "positive", this::checkPositive,
            "range", this::checkRange
    );

    public boolean isValid(Object input) {
        if (!isValidInput(input)) {
            return false;
        }

        for (Map.Entry<String, List<Object>> entry : requirements.entrySet()) {
            String typeOfCheck = entry.getKey();
            List<Object> parameters = entry.getValue();
            boolean isCurrentCheckPass = checkMethods.get(typeOfCheck).test(parameters, input);
            if (!isCurrentCheckPass) {
                return false;
            }
        }

        return true;
    }

    // Section with methods to set up checking requirements
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
    private boolean isValidInput(Object input) {
        return input == null || input instanceof Integer;
    }

    private boolean checkRequired(Object parameters, Object input) {
        return !(input == null);
    }

    private boolean checkPositive(Object parameters, Object input) {
        if (input == null) {
            return true;
        }
        return input instanceof Integer && (int) input > 0;
    }

    private boolean checkRange(Object listOfRanges, Object input) {
        if (input == null) {
            return true;
        }
        if (!(input instanceof Integer) || !(listOfRanges instanceof List<?> ranges)) {
            return false;
        }

        int value = (int) input;
        for (Object range : ranges) {
            if (!(range instanceof int[])) {
                return false;
            }
            int start = ((int[]) range)[0];
            int end = ((int[]) range)[1];

            if (value < start || value > end) {
                return false;
            }
        }

        return true;
    }
}
