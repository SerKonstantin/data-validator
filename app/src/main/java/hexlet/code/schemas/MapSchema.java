package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {
    protected boolean isValidType(Object input) {
        return input instanceof Map<?, ?>;
    }

    public MapSchema required() {
        requiredFlag = true;
        return this;
    }

    public MapSchema sizeof(int size) {
        checks.add(input -> ((Map<?, ?>) input).size() == size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        Predicate<Object> checkShape = input -> {
            Map<?, ?> inputMap = (Map<?, ?>) input;

            return inputMap.entrySet().stream().allMatch(field -> {
                String fieldName = (String) field.getKey();
                BaseSchema schema = schemas.get(fieldName);
                Object fieldValue = field.getValue();
                return schema == null || schema.isValid(fieldValue);
            });
        };

        checks.add(checkShape);
        return this;
    }
}
