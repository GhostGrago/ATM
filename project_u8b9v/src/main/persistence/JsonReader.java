package persistence;

import model.Account;
import model.Entry;
import model.WorkRoom;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
// reference to the example code in phase 2 material


public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkRoom read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonArray = new JSONObject(jsonData);
        return parseWorkRoom(jsonArray);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private WorkRoom parseWorkRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        WorkRoom wr = new WorkRoom(name);
        addAccounts(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses Accounts from JSON object and adds them to workroom
    private void addAccounts(WorkRoom wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("accounts");
        for (Object json : jsonArray) {
            JSONObject nextAcc = (JSONObject) json;
            addAccount(wr, nextAcc);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses Account from JSON object and adds it to workroom
    private void addAccount(WorkRoom wr, JSONObject jsonObject) {
        int id = jsonObject.getInt("ID");
        String name = jsonObject.getString("name");
        Double saving = jsonObject.getDouble("Saving");
        Double chequing = jsonObject.getDouble("Chequing");
        List<Entry> history = transformTransactions(jsonObject);
        Account account = new Account(name, id, saving, chequing);
        account.setHistory((ArrayList) history);
        wr.addAccount(account);
    }

    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private ArrayList<Entry> transformTransactions(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("History");
        ArrayList<Entry> history = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextTransactionJson = (JSONObject) json;
            int amount = nextTransactionJson.getInt("amount");
            String from = nextTransactionJson.getString("from");
            String type = nextTransactionJson.getString("type");
            Entry e = new Entry(type, from, amount);
            history.add(e);
        }
        return history;
    }
}
