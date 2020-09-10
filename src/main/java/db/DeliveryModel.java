package db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DeliveryModel {

    private String name;
    private String address;
    private String date;
    private ObjectMapper objectMapper;

    public DeliveryModel(String name, String address, String date) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.objectMapper = new ObjectMapper();
    }

    public DeliveryModel(String json) {
        this.objectMapper = new ObjectMapper();
        // TODO: maybe fix this?
        this.name = this.getValueFromJson(json, "name");
        this.address = this.getValueFromJson(json, "address");
        this.date = this.getValueFromJson(json, "date");
    }

    @Override
    public String toString() {
        ObjectNode rootNode = objectMapper.createObjectNode();

        rootNode.put("name", this.name);
        rootNode.put("address", this.address);
        rootNode.put("date", this.date);

        String jsonString = null;
        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public String getValueFromJson(String dataJson, String key) {
        String value = null;
        try {
            // Parse the json string and extract the value corresponding to the given key
            JsonNode node = objectMapper.readTree(dataJson);
            value = node.get(key.toLowerCase()).toString();
            value = value.replace("\"", "");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return value;
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
}
