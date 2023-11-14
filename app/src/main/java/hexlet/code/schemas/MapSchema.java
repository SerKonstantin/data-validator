package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapSchema extends BaseSchema {
    // Section to populate checkMethods (map located in superclass)
    {
        checkMethods.put("required", this::checkRequired);
        checkMethods.put("sizeof", this::checkSizeOf);
        checkMethods.put("shape", this::checkShape);
    }

    // Section with methods to populate checking requirements (map located in superclass)
    public MapSchema required() {
        requirements.computeIfAbsent("required", value -> new ArrayList<>()).add(true);
        return this;
    }

    public MapSchema sizeof(int size) {
        requirements.computeIfAbsent("sizeof", value -> new ArrayList<>()).add(size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        requirements.computeIfAbsent("shape", value -> new ArrayList<>()).add(schemas);
        return this;
    }

    // Section with methods to check each condition from requirements
    protected boolean isValidInput(Object input) {
        return input == null || input instanceof Map<?, ?>;
    }

    private boolean checkRequired(Object parameters, Object input) {
        return !(input == null);
    }

    private boolean checkSizeOf(Object listOfSizes, Object input) {
        if (input == null) {
            return true;
        }

        int inputSize = ((Map<?, ?>) input).size();
        List<?> sizes = (List<?>) listOfSizes;

        for (Object size : sizes) {
            if (inputSize != (int) size) {
                return false;
            }
        }
        return true;
    }

    private boolean checkShape(Object parameters, Object input) {
        if (input == null) {
            return true;
        }

        List<?> listOfSchemas = (List<?>) parameters;
        Map<?, ?> inputMap = (Map<?, ?>) input;

        for (Map.Entry<?, ?> field : inputMap.entrySet()) {
            String fieldName = (String) field.getKey();
            Object fieldValue = field.getValue();

            for (Object schemas : listOfSchemas) {
                Map<?, ?> currentRules = (Map<?, ?>) schemas;

                BaseSchema rule = (BaseSchema) currentRules.get(fieldName);
                if (rule != null && !rule.isValid(fieldValue)) {
                    return false;
                }
            }
        }
        return true;
    }
}
