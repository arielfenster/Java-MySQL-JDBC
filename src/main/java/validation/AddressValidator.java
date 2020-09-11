package validation;

import javax.xml.bind.ValidationException;

public class AddressValidator implements IValidator {

    private boolean isInsertOperation;

    public AddressValidator(boolean isInsertOperation) {
        this.isInsertOperation = isInsertOperation;
    }

    @Override
    public void validate(String address) throws ValidationException {
        if (isInsertOperation) {
            validateAdd(address);
        } else {
            validateUpdate(address);
        }
    }

    private void validateAdd(String address) throws ValidationException {
        if (address.split(" ").length < 2) {
            throw new ValidationException("Invalid address. Address should contain at least 2 words");
        }
    }

    private void validateUpdate(String address) throws ValidationException {
        if (address.isEmpty()) {
            return;
        }
        validateAdd(address);
    }
}
