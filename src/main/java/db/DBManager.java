package db;

import java.sql.*;

public class DBManager {

    private Connection connection;

    public void connect(String url, String username, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);

//        Statement statement = this.connection.createStatement();
//        String query = "select * from deliveries";
//
//        ResultSet rs = statement.executeQuery(query);
//
//        while (rs.next()) {
//            String id = rs.getString("id");
//            String data = rs.getString("data");
//            System.out.println(id + " " + data);
//        }
    }

    public boolean add() {
        return true;
    }
}
