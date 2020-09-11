package db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DeliveryModel {

    private String name;
    private String address;
    private String date;
    private String jsonString;
    private ObjectMapper objectMapper;

    private final String KEY_NAME = "name";
    private final String KEY_ADDRESS = "address";
    private final String KEY_DATE = "date";

    public DeliveryModel(String name, String address, String date) {
        this.name = name.trim();
        this.address = address.trim();
        this.date = date.trim();
        this.objectMapper = new ObjectMapper();
        this.jsonString = null;
    }

    public DeliveryModel(String dataJson) {
        this.objectMapper = new ObjectMapper();
        this.jsonString = dataJson;
        this.name = this.getValueFromJson(dataJson, KEY_NAME);
        this.address = this.getValueFromJson(dataJson, KEY_ADDRESS);
        this.date = this.getValueFromJson(dataJson, KEY_DATE);
    }

    public String getValueFromJson(String dataJson, String key) {
        String value = null;
        try {
            // Parse the json string and extract the value corresponding to the given key
            JsonNode node = objectMapper.readTree(dataJson);
            value = node.get(key.toLowerCase()).toString().trim();
            value = value.replace("\"", "");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            value = null;
        }
        return value;
    }

    @Override
    public String toString() {
        if (jsonString != null) {
            return jsonString;
        }
        ObjectNode rootNode = objectMapper.createObjectNode();

        rootNode.put(KEY_NAME, name);
        rootNode.put(KEY_ADDRESS, address);
        rootNode.put(KEY_DATE, date);

        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public boolean isEqualTo(DeliveryModel other) {
        String otherName = other.getName();
        String otherAddress = other.getAddress();
        String otherDate = other.getDate();

        boolean areNamesEqual;
        if (otherName.isEmpty()) {
            areNamesEqual = true;
        } else {
            areNamesEqual = this.name.compareTo(otherName) == 0;
        }

        boolean areAddressesEqual;
        if (otherAddress.isEmpty()) {
            areAddressesEqual = true;
        } else {
            areAddressesEqual = this.address.compareTo(otherAddress) == 0;
        }

        boolean areDatesEqual;
        if (otherDate.isEmpty()) {
            areDatesEqual = true;
        } else {
            areDatesEqual = this.date.compareTo(otherDate) == 0;
        }

        return areNamesEqual && areAddressesEqual && areDatesEqual;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        if (!name.isEmpty()) {
            this.name = name;
            this.jsonString = null;
        }
    }

    public void setAddress(String address) {
        if (!address.isEmpty()) {
            this.address = address;
            this.jsonString = null;
        }

    }

    public void setDate(String date) {
        if (!date.isEmpty()) {
            this.date = date;
            this.jsonString = null;
        }
    }
}
