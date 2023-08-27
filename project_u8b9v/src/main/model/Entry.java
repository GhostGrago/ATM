package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a transaction history entry that has the transaction type, origin of funding and the amount of
// money involved
public class Entry implements Writable {
    private String type;
    private String from;
    private double amount;

    public Entry(String type, String from, double amount) {
        this.type = type;
        this.from = from;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }

    public double getAmount() {
        return amount;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("from", from);
        json.put("amount", amount);
        return json;
    }
}

