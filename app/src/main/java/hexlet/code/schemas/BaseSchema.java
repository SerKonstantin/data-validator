package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema {
    protected List<Predicate<Object>> checks;
    protected boolean requiredFlag;

    protected BaseSchema() {
        this.checks = new ArrayList<>();
        this.requiredFlag = false;
    }

    public BaseSchema required() {
        requiredFlag = true;
        return this;
    }

    protected abstract boolean isValidInput(Object input);

    public final boolean isValid(Object input) {
        if (!isValidInput(input)) {
            return false;
        }

        if (input == null) {
            return true;
        }

        for (Predicate<Object> check : checks) {
            boolean isCurrentCheckPass = check.test(input);
            if (!isCurrentCheckPass) {
                return false;
            }
        }

        return true;
    }
}
