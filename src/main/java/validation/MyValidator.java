package validation;


public class MyValidator implements IValidator {

    @Override
    public void validateName(String name) {
        if (name.length() < 3) {
            throw new RuntimeException("Name should contain at least 3 characters");
        }
    }

    @Override
    public void validateAddress(String address) {
        if (address.split(" ").length < 2) {
            throw new RuntimeException("Address should contain at least 2 words");
        }
    }

    @Override
    public void validateHours(String hours) {
//        return false;
    }
}
