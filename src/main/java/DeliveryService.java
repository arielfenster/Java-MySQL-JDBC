import db.DBManager;
import db.DeliveryModel;
import validation.IValidator;
import validation.MyValidator;

import java.util.List;
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

    }

    private void viewActiveDeliveries() {
        List<String> activeDeliveries = dbManager.getAllActiveDeliveries();

        for (String str : activeDeliveries) {
            System.out.println(str);
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
