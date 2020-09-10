package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private Connection connection;

    public void connect(String url, String username, String password) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*
        Statement statement = this.connection.createStatement();
        String query = "select * from deliveries";

        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            String id = rs.getString("id");
            String data = rs.getString("data");
            System.out.println(id + " " + data);
         */
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
}
