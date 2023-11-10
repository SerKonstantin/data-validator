package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringSchema {
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
                case "minLength" -> parameters.stream().allMatch(parameter -> checkMinLength(parameter, input));
                case "substring" -> parameters.stream().allMatch(parameter -> checkSubstring(parameter, input));
                default -> throw new RuntimeException();
            };

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

    private boolean checkRequired(Object input) {
        return !(input == null || input.equals(""));
    }

    private boolean checkMinLength(Object length, Object input) {
        if (input == null) {
            return true;
        }

        if (!(input instanceof String) || !(length instanceof Integer)) {
            return false;
        }
        return input.toString().length() >= (int) length;
    }

    private boolean checkSubstring(Object substring, Object input) {
        if (input == null) {
            return true;
        }

        if (!(input instanceof String) || !(substring instanceof String)) {
            return false;
        }
        return input.toString().contains((String) substring);
    }
}
