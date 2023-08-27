package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a workroom having a collection of accounts
// reference to the example code in phase 2 material

public class WorkRoom implements Writable {
    private String name;
    private List<Account> accounts;

    // EFFECTS: constructs workroom with a name and empty list of accounts
    public WorkRoom(String name) {
        this.name = name;
        accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds account to this workroom
    public void addAccount(Account account) {
        accounts.add(account);
        EventLog.getInstance().logEvent(new Event("Account Created for " + account.getName()));

    }

    // EFFECTS: returns an unmodifiable list of accounts in this workroom
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    // EFFECTS: returns number of accounts in this workroom
    public int numAccounts() {
        return accounts.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("accounts", accountsToJson());
        return json;
    }

    // EFFECTS: returns accounts in this workroom as a JSON array
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account t : accounts) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


}


