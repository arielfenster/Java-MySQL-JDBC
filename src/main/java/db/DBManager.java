package db;

import utils.Helper;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private Connection connection;

    public void connect(String url, String username, String password) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(DeliveryModel deliveryModel) {
        try {
            Statement statement = this.connection.createStatement();
            String query = String.format("INSERT INTO deliveries(data) values('%s')", deliveryModel.toString());
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllActiveDeliveries() {
        List<String> activeDeliveries = new ArrayList<>();

        try {
            // Extract all the deliveries from the db
            Statement statement = this.connection.createStatement();
            String query = "SELECT data from deliveries";
            ResultSet resultSet = statement.executeQuery(query);

            LocalTime currentTime = LocalTime.now();

            while (resultSet.next()) {
                // Create a delivery model object based on the data value in the db
                String delivery = resultSet.getString(1);
                DeliveryModel deliveryModel = new DeliveryModel(delivery);

                // Check if the delivery's time value is after the current time. If yes then add it to the list
                LocalTime deliveryTime = Helper.convertStringToTimeWithDefaultFormat(deliveryModel.getDate());
                if (deliveryTime.isAfter(currentTime)) {
                    activeDeliveries.add(delivery);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeDeliveries;
    }
}
