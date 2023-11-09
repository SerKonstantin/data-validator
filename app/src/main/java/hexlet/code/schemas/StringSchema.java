package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringSchema {
    private final Map<String, List<Object>> requirements = new HashMap<>();

    public boolean isValid(Object input) {
        // Check if input is String or null
        if (!(input == null || input instanceof String)) {
            return false;
        }

        boolean isAllChecksPass = true;

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
                isAllChecksPass = false;
            }
        }

        return isAllChecksPass;
    }

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

    private boolean checkRequired(Object input) {
        return !(input == null || input.equals(""));
    }

    private boolean checkMinLength(Object length, Object input) {
        try {
            return input.toString().length() >= (int) length;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkSubstring(Object substring, Object input) {
        try {
            return input.toString().contains((String) substring);
        } catch (Exception e) {
            return false;
        }
    }
}
