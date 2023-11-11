package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class StringSchema {
    private final Map<String, List<Object>> requirements = new HashMap<>();
    private final Map<String, BiPredicate<Object, Object>> checkMethods = Map.of(
            "required", this::checkRequired,
            "minLength", this::checkMinLength,
            "substring", this::checkSubstring
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
    public StringSchema required() {
        requirements.computeIfAbsent("required", value -> new ArrayList<>()).add(true);
        return this;
    }

    public StringSchema minLength(int minLength) {
        requirements.computeIfAbsent("minLength", value -> new ArrayList<>()).add(minLength);
        return this;
    }

    public StringSchema contains(String substring) {
        requirements.computeIfAbsent("substring", value -> new ArrayList<>()).add(substring);
        return this;
    }

    // Section with methods to check each condition from requirements
    private boolean isValidInput(Object input) {
        return input == null || input instanceof String;
    }

    private boolean checkRequired(Object parameters, Object input) {
        return !(input == null || input.equals(""));
    }

    private boolean checkMinLength(Object listOfLengths, Object input) {
        if (input == null) {
            return true;
        }
        if (!(input instanceof String) || !(listOfLengths instanceof List<?> lengths)) {
            return false;
        }

        for (Object length : lengths) {
            if (!(length instanceof Integer)) {
                return false;
            }
            if (input.toString().length() < (int) length) {
                return  false;
            }
        }

        return true;
    }

    private boolean checkSubstring(Object listOfSubstrings, Object input) {
        if (input == null) {
            return true;
        }
        if (!(input instanceof String) || !(listOfSubstrings instanceof List<?> substrings)) {
            return false;
        }

        for (Object substring : substrings) {
            if (!(substring instanceof String)) {
                return false;
            }
            if (!input.toString().contains((String) substring)) {
                return false;
            }
        }

        return true;
    }
}
