package db;

import utils.TimeConverter;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBManager {

    private Connection connection;
    private HashMap<Integer, DeliveryModel> deliveries;

    public DBManager() {
        this.deliveries = new HashMap<>();
    }

    public void connect(String url, String username, String password) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(DeliveryModel deliveryModel) {
        try {
            // Create the query and execute it
            Statement statement = this.connection.createStatement();
            String query = String.format("INSERT INTO deliveries(data) values('%s')", deliveryModel.toString());
            statement.executeUpdate(query);

            // Add the delivery to the map
            int id = statement.getGeneratedKeys().getInt("id");
            deliveries.put(id, deliveryModel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, DeliveryModel oldDelivery, DeliveryModel newDelivery) {
        oldDelivery.setName(newDelivery.getName());
        oldDelivery.setAddress(newDelivery.getAddress());
        oldDelivery.setDate(newDelivery.getDate());

        try {
            // Create the query and execute it
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE deliveries SET data='%s' WHERE id=%d", oldDelivery.toString(), id);
            statement.executeUpdate(query);

            // Update the map
            deliveries.put(id, oldDelivery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getAllDeliveriesFromDB() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * from deliveries";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String data = resultSet.getString("data");
                DeliveryModel deliveryModel = new DeliveryModel(data);

                deliveries.put(id, deliveryModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, DeliveryModel> getAllActiveDeliveries() {
        if (deliveries.isEmpty()) {
            this.getAllDeliveriesFromDB();
        }
        HashMap<Integer, DeliveryModel> active = new HashMap<>(deliveries);

        LocalTime currentTime = TimeConverter.getCurrentTimeWithDefaultFormat();
        active.entrySet().removeIf(deliveryItem -> {
            LocalTime deliveryTime = TimeConverter.convertStringToTimeWithDefaultFormat(deliveryItem.getValue().getDate());
            return deliveryTime.isBefore(currentTime);
        });

        return active;
    }

    public List<DeliveryModel> getAllActiveDeliveriesList() {
        // TODO: maybe convert to a member? so the function won't have to calculate every single time
        List<DeliveryModel> activeDeliveries = new ArrayList<>();

        try {
            // Extract all the deliveries from the db
            Statement statement = this.connection.createStatement();
            String query = "SELECT * from deliveries";
            ResultSet resultSet = statement.executeQuery(query);

            LocalTime currentTime = TimeConverter.getCurrentTimeWithDefaultFormat();

            while (resultSet.next()) {
                // Create a delivery model object based on the data value in the db
                String id = resultSet.getString("id");
                String delivery = resultSet.getString("data");
                DeliveryModel deliveryModel = new DeliveryModel(delivery);

                // Check if the delivery's time value is after the current time. If yes then add it to the list
                LocalTime deliveryTime = TimeConverter.convertStringToTimeWithDefaultFormat(deliveryModel.getDate());
                if (deliveryTime.isAfter(currentTime)) {
                    activeDeliveries.add(deliveryModel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeDeliveries;
    }
}
