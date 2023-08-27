package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents an account having an id, owner name and balance of chequing and saving(in dollars), list of transaction
// history entries
public class Account implements Writable {
    private int id;                        // account id
    private String name;                   // the account owner name
    private double chequing;               // balance of chequing account
    private double saving;                 // balance of saving account
    private ArrayList<Entry> history;      // transaction history


    //  REQUIRES: accountName has a non-zero length
//            account id is a positive integer not assigned to any other account;
//  EFFECTS: name on account is set to name; with an ID, saving and chequing balance
    public Account(String name, int id, double saving, double chequing) {
        this.name = name;
        this.id = id;
        this.saving = saving;
        this.chequing = chequing;
        this.history = new ArrayList<>();

    }

    //REQUIRES: int amount >= 0
    //MODIFIES: this
    //EFFECTS: deposit given amount to chequing account, make a new history entry
    public void deposit(double amount) {
        this.chequing += amount;
        this.history.add(new Entry("deposit", "chequing", amount));
        EventLog.getInstance().logEvent(new Event(this.name + " deposited $" + amount));

    }

    //REQUIRES: int amount >= 0
    //MODIFIES: this
    //EFFECTS: withdraw given amount to chequing account, make a new history entry
    public void withdraw(double amount) {
        this.chequing -= amount;
        this.history.add(new Entry("withdraw", "chequing", amount));
        EventLog.getInstance().logEvent(new Event(this.name + " withdrew $" + amount));

    }

    //REQUIRES: int amount >= 0
    //MODIFIES: this
    //EFFECTS: withdraw given amount from chequing account to saving account
    public void transfer(double amount) {
        if (amount <= this.chequing) {
            this.chequing -= amount;
            this.saving += amount;
            System.out.println("Transfer successful! " + amount + " has been moved to saving account");
            this.history.add(new Entry("transfer", "saving", amount));
            EventLog.getInstance().logEvent(new Event(this.name + "transferred $" + amount + "to saving"));
        } else {
            System.out.println("Insufficient funds in chequing account.");
        }
    }

    //EFFECTS: print account summary: balance of both account and history
    public void printSummary() {
        System.out.println("\tChequing account balance: $" + getChequing());
        System.out.println("\tSaving account balance: $" + getSaving());
        System.out.println("\tTransaction History:" + summary());
    }

    //EFFECTS: turn transaction entries in the list to human-readable string
    public String summary() {
        String sum = "";
        int index = 1;
        for (Entry e : history) {
            sum = sum + "\n\t"
                    + index + ". | "
                    + "Activity: " + e.getType()
                    + "| Account: " + e.getFrom()
                    + "| Amount: " + e.getAmount();
            index++;
        }
        return sum;
    }

    //EFFECTS: generate a random rate between 0.015 and 0.05
    public double calculateRate() {
        Random rand = new Random();
        double interestRate = 0.015 + rand.nextDouble() * 0.035; // random rate between 1.5% and 5%
        return interestRate;
    }
    //EFFECT: Calculate credit score based on number of total transactions and amount.

    public int calculateScore() {
        int score = 500 + this.numTransactions() * 50 + this.totalAmount() / 1000;
        return score;
    }
    //EFFECTS: calculate total amount of transaction history

    public int totalAmount() {
        int num = 0;
        for (Entry e : history) {
            num += e.getAmount();
        }
        return num;
    }
    //EFFECTS: return number of transaction histories

    public int numTransactions() {
        return history.size();
    }

    //getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getChequing() {
        return chequing;
    }

    public double getSaving() {
        return saving;
    }

    public List<Entry> getHistory() {
        return history;
    }

    public void setHistory(ArrayList his) {
        this.history = his;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("ID", id);
        json.put("Chequing", chequing);
        json.put("Saving", saving);
        json.put("History", transactionsToJson());
        return json;
    }

    // EFFECTS: returns transactions in savings account in this workroom as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Entry e : history) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }
}
