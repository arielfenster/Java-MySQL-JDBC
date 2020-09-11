package db;

import utils.TimeConverter;

import java.sql.*;
import java.time.LocalTime;
import java.util.HashMap;

public class MySqlManager implements IDatabaseManager {

    private Connection connection;
    private HashMap<Integer, DeliveryModel> deliveries;

    public MySqlManager() {
        this.deliveries = new HashMap<>();
    }

    @Override
    public boolean connect(String url, String username, String password) {
        boolean isConnected;

        try {
            this.connection = DriverManager.getConnection(url, username, password);
            isConnected = true;
        } catch (SQLException e) {
            e.printStackTrace();
            isConnected = false;
        }
        return isConnected;
    }

    @Override
    public void insert(DeliveryModel deliveryModel) {
        try {
            // Create the query and execute it (and get the auto-generated id from the db)
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO deliveries(data) values('%s')", deliveryModel.toString());
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            // Add the delivery to the map
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                deliveries.put(id, deliveryModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, DeliveryModel oldDelivery, DeliveryModel newDelivery) {
        // If the new data is the same as the old data then there is no need to execute a query
        if (oldDelivery.isEqualTo(newDelivery)) {
            return;
        }

        // Update the old model with the new data (ignoring empty inputs)
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

    public HashMap<Integer, DeliveryModel> getAllActiveDeliveries() {
        if (deliveries.isEmpty()) {
            this.getAllDeliveriesFromDB();
        }

        // Iterate over all the existing deliveries and filter out all the ones that are in the past
        HashMap<Integer, DeliveryModel> active = new HashMap<>(deliveries);

        LocalTime currentTime = TimeConverter.getCurrentTimeWithDefaultFormat();

        active.entrySet().removeIf(deliveryItem -> {
            LocalTime deliveryTime = TimeConverter.convertStringToTimeWithDefaultFormat(deliveryItem.getValue().getDate());
            return deliveryTime.isBefore(currentTime);
        });

        return active;
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

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
