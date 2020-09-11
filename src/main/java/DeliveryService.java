import db.DBManager;
import db.DeliveryModel;
import validation.AddressValidator;
import validation.DateValidator;
import validation.IValidator;
import validation.NameValidator;

import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DeliveryService {

    private DBManager dbManager;
    private IValidator validator;
    private Scanner scanner;

    public DeliveryService(DBManager dbManager, IValidator validator) {
        this.dbManager = dbManager;
        this.validator = validator;
        this.scanner = new Scanner(System.in);
    }

    public DeliveryService(DBManager dbManager) {
        this.dbManager = dbManager;
        this.scanner = new Scanner(System.in);
    }

    private String receiveInput(String msg, IValidator validator) {
        String data;
        System.out.println(msg);

        try {
            data = scanner.nextLine();
            validator.validate(data);
        } catch (ValidationException e) {
            System.err.println(e.getMessage() + "\n");
            data = null;
        }
        return data;
    }

    /*
    private DeliveryModel createDeliveryModel() {
        String name = receiveInput("Enter recipient name", new NameValidator(IValidator.Operation.INSERT));
        if (name == null) return null;

        String address = receiveInput("Enter delivery address", new AddressValidator(IValidator.Operation.INSERT));
        if (address == null) return null;

        String hour = receiveInput("Enter delivery hour", new DateValidator(IValidator.Operation.INSERT));
        if (hour == null) return null;

        return new DeliveryModel(name, address, hour);
    }

     */

    private void insertDelivery() {
        String name = receiveInput("Enter recipient name", new NameValidator(IValidator.Operation.INSERT));
        if (name == null) return;

        String address = receiveInput("Enter delivery address", new AddressValidator(IValidator.Operation.INSERT));
        if (address == null) return;

        String hour = receiveInput("Enter delivery hour", new DateValidator(IValidator.Operation.INSERT));
        if (hour == null) return;

        DeliveryModel deliveryModel = new DeliveryModel(name, address, hour);
        dbManager.insert(deliveryModel);
    }

    private Integer getUpdateDeliveryId(HashMap<Integer, DeliveryModel> activeDeliveries) {
        // Get user's input of requested id
        System.out.println("Enter the id of the delivery which you want to edit");
        int id = Integer.parseInt(scanner.nextLine());

        // Check that the user entered a valid id
        if (!activeDeliveries.containsKey(id)) {
            System.err.println("Invalid id. Need to enter an existing id\n");
            return null;
        }
        return id;
    }

    private void updateDelivery() {
        HashMap<Integer, DeliveryModel> activeDeliveries = dbManager.getAllActiveDeliveries();

        // Get the requested delivery's id
        Integer id = getUpdateDeliveryId(activeDeliveries);
        if (id == null) return;

        // For each field, show the current value of the requested delivery and get the new inputs (and validate them)
        DeliveryModel currentDelivery = activeDeliveries.get(id);

        String newName = receiveInput(String.format("Enter recipient name (%s): ", currentDelivery.getName()), new NameValidator(IValidator.Operation.UPDATE));
        if (newName == null) return;

        String newAddress = receiveInput(String.format("Enter delivery address (%s): ", currentDelivery.getAddress()), new AddressValidator(IValidator.Operation.UPDATE));
        if (newAddress == null) return;

        String newDate = receiveInput(String.format("Enter delivery hour (%s): ", currentDelivery.getDate()), new DateValidator(IValidator.Operation.UPDATE));
        if (newDate == null) return;

        DeliveryModel newDelivery = new DeliveryModel(newName, newAddress, newDate);
        dbManager.update(id, currentDelivery, newDelivery);
    }

    private void viewActiveDeliveries() {
        HashMap<Integer, DeliveryModel> activeDeliveries = dbManager.getAllActiveDeliveries();

        System.out.println("All active deliveries:");
        for (Map.Entry<Integer, DeliveryModel> entry : activeDeliveries.entrySet()) {
            System.out.println(String.format("ID: %d. Data: %s", entry.getKey(), entry.getValue().toString()));
        }
    }

    private void displayOptions() {
        System.out.println("1. Insert new delivery");
        System.out.println("2. Update delivery");
        System.out.println("3. View active deliveries");
        System.out.println("4. Exit");
    }

    public void run() {
        // Database connection data
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        String username = "root";
        String password = "fenster1w2k";

        // Connect to database
        dbManager.connect(url, username, password);

        while (true) {
            displayOptions();

            int input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 1: {
                    insertDelivery();
                    break;
                }
                case 2: {
                    viewActiveDeliveries();
                    updateDelivery();
                    break;
                }
                case 3: {
                    viewActiveDeliveries();
                    System.out.println("Click enter to continue");
                    scanner.nextLine();
                    break;
                }
                case 4: {
                    return;
                }
            }
        }
    }

    public void close() {
        dbManager.close();
        scanner.close();
    }

    public static void main(String[] args) {
//        DeliveryService deliveryService = new DeliveryService(new DBManager(), new MyValidator());
        DeliveryService deliveryService = new DeliveryService(new DBManager());
        deliveryService.run();

        deliveryService.close();
    }
}
