package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {
    protected boolean isValidInput(Object input) {
        if (requiredFlag) {
            return input instanceof Map<?, ?>;
        } else {
            return input == null || input instanceof Map<?, ?>;
        }
    }

    public MapSchema sizeof(int size) {
        Predicate<Object> checkSizeof = input -> ((Map<?, ?>) input).size() == size;
        checks.add(checkSizeof);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        Predicate<Object> checkShape = input -> {
            Map<?, ?> inputMap = (Map<?, ?>) input;
            for (Map.Entry<?, ?> field : inputMap.entrySet()) {
                String fieldName = (String) field.getKey();
                BaseSchema schema = schemas.get(fieldName);

                Object fieldValue = field.getValue();
                if (schema != null && !schema.isValid(fieldValue)) {
                    return false;
                }
            }

            return true;
        };

        checks.add(checkShape);
        return this;
    }
}
