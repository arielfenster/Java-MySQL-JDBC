package validation;

import javax.xml.bind.ValidationException;

public class NameValidator implements IValidator {

    private boolean isInsertOperation;

    public NameValidator(boolean isInsertOperation) {
        this.isInsertOperation = isInsertOperation;
    }

    @Override
    public void validate(String name) throws ValidationException {
        if (isInsertOperation) {
            validateAdd(name);
        } else {
            validateUpdate(name);
        }
    }

    private void validateAdd(String name) throws ValidationException {
        if (name.length() < 3) {
            throw new ValidationException("Invalid name. Name should contain at least 3 characters");
        }
    }

    private void validateUpdate(String name) throws ValidationException {
        if (name.isEmpty()) {
            return;
        }
        validateAdd(name);
    }
}
