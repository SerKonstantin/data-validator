package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberSchema {
    private final Map<String, List<Object>> requirements = new HashMap<>();

    public boolean isValid(Object input) {
        if (!isValidInput(input)) {
            return false;
        }

        for (Map.Entry<String, List<Object>> entry : requirements.entrySet()) {
            String typeOfCheck = entry.getKey();
            List<Object> parameters = entry.getValue();

            boolean isCurrentCheckPass = switch (typeOfCheck) {
                case "required" -> checkRequired(input);
                case "positive" -> checkPositive(input);
                case "range" -> parameters.stream().allMatch(parameter -> checkRange(parameter, input));
                default -> throw new RuntimeException();
            };

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

    private boolean checkRequired(Object input) {
        return !(input == null);
    }

    private boolean checkPositive(Object input) {
        if (input == null) {
            return true;
        }
        return input instanceof Integer && (int) input > 0;
    }

    private boolean checkRange(Object range, Object input) {
        if (input == null) {
            return true;
        }

        if (!(input instanceof Integer) || !(range instanceof int[] rangeBorders)) {
            return false;
        }
        int value = (int) input;
        int start = rangeBorders[0];
        int end = rangeBorders[1];

        return (start <= value && value <= end);
    }
}
