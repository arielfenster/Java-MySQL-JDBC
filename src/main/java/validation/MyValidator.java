//package validation;
//
//
//import utils.TimeConverter;
//
//import javax.xml.bind.ValidationException;
//import java.time.LocalTime;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class MyValidator implements IValidator {
//
//    @Override
//    public void validateName(String name) throws ValidationException {
//        if (name.length() < 3) {
//            throw new ValidationException("Invalid name. Name should contain at least 3 characters");
//        }
//    }
//
//    @Override
//    public void validateAddress(String address) throws ValidationException {
//        if (address.split(" ").length < 2) {
//            throw new ValidationException("Invalid address. Address should contain at least 2 words");
//        }
//    }
//
//    @Override
//    public void validateDate(String date) throws ValidationException {
//        // Check the input's format
//        Pattern pattern = Pattern.compile("\\d\\d:\\d\\d");
//        Matcher matcher = pattern.matcher(date);
//        if (!matcher.find()) {
//            throw new ValidationException("Invalid date. Date input needs to be in HH:mm format");
//        }
//
//        // Check that the input is in the future
//        LocalTime inputTime = TimeConverter.convertStringToTimeWithDefaultFormat(date);
//        LocalTime currentTime = TimeConverter.getCurrentTimeWithDefaultFormat();
//
//        if (inputTime.isBefore(currentTime)) {
//            throw new ValidationException("Invalid date. Delivery time must be in the future");
//        }
//    }
//}
