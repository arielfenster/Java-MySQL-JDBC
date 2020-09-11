package validation;

import utils.TimeConverter;

import javax.xml.bind.ValidationException;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator implements IValidator {

    private boolean isInsertOperation;

    public DateValidator(boolean isInsertOperation) {
        this.isInsertOperation = isInsertOperation;
    }

    @Override
    public void validate(String date) throws ValidationException {
        if (isInsertOperation) {
            validateAdd(date);
        } else {
            validateUpdate(date);
        }
    }

    private void validateAdd(String date) throws ValidationException {
        // Check the input's format
        Pattern pattern = Pattern.compile("\\d\\d:\\d\\d");
        Matcher matcher = pattern.matcher(date);
        if (!matcher.find()) {
            throw new ValidationException("Invalid date. Date input needs to be in HH:mm format");
        }

        // Check that the input is in the future
        LocalTime inputTime = TimeConverter.convertStringToTimeWithDefaultFormat(date);
        LocalTime currentTime = TimeConverter.getCurrentTimeWithDefaultFormat();

        if (inputTime.isBefore(currentTime)) {
            throw new ValidationException("Invalid date. Delivery time must be in the future");
        }
    }

    private void validateUpdate(String date) throws ValidationException {
        if (date.isEmpty()) {
            return;
        }
        validateAdd(date);
    }
}
