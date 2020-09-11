import db.DBManager;
import db.DeliveryModel;
import validation.IValidator;
import validation.MyValidator;

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

    private void insertDelivery() {
        System.out.println("Enter recipient name");
        String name = scanner.nextLine();
        validator.validateName(name);

        System.out.println("Enter delivery address");
        String address = scanner.nextLine();
        validator.validateAddress(address);

        System.out.println("Enter delivery hour");
        String hour = scanner.nextLine();
        validator.validateDate(hour);

        DeliveryModel deliveryModel = new DeliveryModel(name, address, hour);
        dbManager.insert(deliveryModel);
    }


    private void updateDelivery() {
        HashMap<Integer, DeliveryModel> activeDeliveries = dbManager.getAllActiveDeliveries();

        System.out.println("All future deliveries:");
        for (Map.Entry<Integer, DeliveryModel> entry : activeDeliveries.entrySet()) {
            System.out.println(String.format("ID: %d. Data: %s", entry.getKey(), entry.getValue().toString()));
        }

        // Get user's input
        System.out.println("Enter the delivery's id which you want to edit");
        int id = Integer.parseInt(scanner.nextLine());

        // Check that the user entered a valid id
        if (!activeDeliveries.containsKey(id)) {
            System.out.println("Invalid id. Need to enter an existing id");
            return;
        }

        // For each field, show the current value of the requested delivery and get the new inputs (and validate them)
        DeliveryModel currentDelivery = activeDeliveries.get(id);

        System.out.println(String.format("Enter recipient name (%s): ", currentDelivery.getName()));
        String updatedName = scanner.nextLine();
        if (updatedName.length() != 0) {
            validator.validateName(updatedName);
        }

        System.out.println(String.format("Enter delivery address (%s): ", currentDelivery.getAddress()));
        String updatedAddress = scanner.nextLine();
        if (updatedAddress.isEmpty()) {
            updatedAddress = currentDelivery.getAddress();
        } else {
            validator.validateAddress(updatedAddress);
        }
//        if (updatedAddress.length() != 0) {
//        }

        System.out.println(String.format("Enter delivery hour (%s): ", currentDelivery.getDate()));
        String updatedDate = scanner.nextLine();
        if (updatedDate.length() != 0) {
            validator.validateDate(updatedDate);
        }

        DeliveryModel updatedDelivery = new DeliveryModel(updatedName, updatedAddress, updatedDate);
        dbManager.update(id, currentDelivery, updatedDelivery);
    }

    private void viewActiveDeliveries() {
        HashMap<Integer, DeliveryModel> activeDeliveries = dbManager.getAllActiveDeliveries();

        for (DeliveryModel deliveryModel : activeDeliveries.values()) {
            System.out.println(deliveryModel.toString());
        }
        System.out.println("Click enter to continue");
        scanner.nextLine();
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
        String password = "password"; // TODO: change this before every time you commit

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
                    updateDelivery();
                    break;
                }
                case 3: {
                    viewActiveDeliveries();
                    break;
                }
                default:
                    return;
            }
        }
    }

    public static void main(String[] args) {
        DeliveryService deliveryService = new DeliveryService(new DBManager(), new MyValidator());
        deliveryService.run();
    }
}
