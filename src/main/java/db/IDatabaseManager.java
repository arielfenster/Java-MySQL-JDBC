package db;

import java.util.HashMap;

public interface IDatabaseManager {

    boolean connect(String url, String username, String password);

    void insert(DeliveryModel deliveryModel);

    void update(int id, DeliveryModel oldDelivery, DeliveryModel newDelivery);

    HashMap<Integer, DeliveryModel> getAllActiveDeliveries();

    void close();
}
