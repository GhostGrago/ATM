package ui;

import model.Account;
import model.WorkRoom;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//A class representing a banking system with the ability to register new accounts, login to existing accounts, and
//perform various transactions on accounts. The system also has the ability to calculate credit scores and mortgage
//payments based on account data.

public class Bank {
    private static final String JSON_STORE = "./data/workroom.json";
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private WorkRoom workRoom;
    private double interestRate;
    private int score;
    private boolean running;
    private Account currentAccount;

    //EFFECTS:runs the bank application
    public Bank() {
        runBank();
    }

    // MODIFIES: this
    // EFFECTS: print menu and processes user input
    public void runBank() {
        running = true;
        String command;
        input = new Scanner(System.in);
        interestRate = getInterestRate();
        workRoom = new WorkRoom("Main Workroom");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        addTestUser();
        displayMenu();

        while (running) {
            command = input.next();
            if (command.equals("5")) {
                running = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nHave a nice day ");
    }
    

    // MODIFIES: this
    // EFFECTS: add a test user to bank
    private void addTestUser() {
        Account kai = new Account("Kai", 123, 0,0);
        workRoom.addAccount(kai);
    }

    // EFFECTS: Displays the main menu of options to the user
    private void displayMenu() {
        System.out.println("\nWelcome, select option below:");
        System.out.println("\t1 -> Login Account");
        System.out.println("\t2 -> Register New Account");
        System.out.println("\t3 -> Save Current File");
        System.out.println("\t4 -> Load Last Save");
        System.out.println("\t5 -> Quit");
    }

    //EFFECTS: Processes the user's main menu command
    public void processCommand(String command) {

        if (command.equals("1")) {
            login();
        } else if (command.equals("2")) {
            register();
        } else if (command.equals("3")) {
            save();
            displayMenu();
        } else if (command.equals("4")) {
            load();
            displayMenu();
        }  else {
            System.out.println("Invalid selection");
        }
    }

    // EFFECTS: saves the workroom to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(workRoom);
            jsonWriter.close();
            System.out.println("Saved " + workRoom.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void load() {
        try {
            workRoom = jsonReader.read();
            System.out.println("Loaded " + workRoom.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: Login a user by prompting them for their user ID and opening a sub-menu
    public void login() {
        int id;
        System.out.println("Type your user ID:");
        id = input.nextInt();

        if (accountExist(id)) {
            currentAccount = getAccount(id);
            menu2();
            String command2 = input.next();
            processCommand2(command2);
        } else {
            System.out.println("Account does not exists!");
            displayMenu();
        }
    }

    //EFFECTS: print second level menu
    public void menu2() {
        System.out.println("\nWelcome " + currentAccount.getName() + "\nSelect service:");
        System.out.println("\t1 -> Deposit to Chequing");
        System.out.println("\t2 -> Transfer to Saving");
        System.out.println("\t3 -> View Credit Score");
        System.out.println("\t4 -> Mortgage/Loan Calculator");
        System.out.println("\t5 -> Print Account Summary");
        System.out.println("\t6 -> Return to Menu");
    }

    //EFFECTS: Processes the user's second menu command
    public void processCommand2(String command) {
        if (command.equals("1")) {
            depositChequing();
            returnMenu2();
        } else if (command.equals("2")) {
            transfer();
            returnMenu2();
        } else if (command.equals("3")) {
            creditScore();
            returnMenu2();
        } else if (command.equals("4")) {
            mortgage();
            returnMenu2();
        } else if (command.equals("5")) {
            summary();
        } else if (command.equals("6")) {
            returnMenu1();
        }  else {
            System.out.println("Invalid selection");
            returnMenu2();
        }
    }

    //EFFECTS: print the summary of current account
    public void summary() {
        currentAccount.printSummary();
        System.out.println("Enter '1' to go back, '2' to go to main menu");
        String command = input.next();
        if (command.equals("1")) {
            returnMenu2();
        } else {
            returnMenu1();
        }
    }

    //MODIFIES: this
    //EFFECTS: display main menu and accepting input for processing
    private void returnMenu1() {
        while (running) {
            displayMenu();
            String command = input.next();
            if (command.equals("5")) {
                running = false;
            } else {
                processCommand(command);
            }
        }
    }


    //EFFECTS: display second menu and accepting input for processing
    private void returnMenu2() {
        menu2();
        String command2 = input.next();
        processCommand2(command2);
    }

    //MODIFIES: this
    //EFFECTS: calculate credit score with method in Account and assign to score
    private void creditScore() {
        score = currentAccount.calculateScore();
        System.out.println("Your Credit Score is " + score);
    }

    //MODIFIES: this
    //EFFECTS: calculate interest rate and monthly payment with method in Account and assign to interestRate, then
    //         print out summary
    public void mortgage() {
        System.out.print("Enter the amount of mortgage/loan: $");
        double loanAmount = input.nextDouble();
        if (interestRate == 0) {
            interestRate = currentAccount.calculateRate();
        }
        double monthlyPayment = loanAmount * (interestRate / 12) / (1 - Math.pow(1 + interestRate / 12, -12 * 5));
        DecimalFormat numberFormat = new DecimalFormat("#.00");

        System.out.println("Your monthly payment for a 5-year loan at "
                + numberFormat.format(interestRate * 100)
                + "% interest rate is: $"
                + numberFormat.format(monthlyPayment));
    }

    //MODIFIES: this
    //EFFECTS: deposit the input amount to chequing account
    public void depositChequing() {
        System.out.println("Type the deposit amount:");
        double amount = input.nextInt();
        currentAccount.deposit(amount);
        System.out.println("Deposit Successful!");
    }

    //MODIFIES: this
    //EFFECTS: transfer the input amount from chequing account to saving account
    public void transfer() {
        System.out.println("Type the amount to transfer:");
        double amount = input.nextInt();
        currentAccount.transfer(amount);
    }

    //EFFECTS: check if the account with the given id exists
    public boolean accountExist(int id) {
        for (Account acc : workRoom.getAccounts()) {
            if (acc.getId() == id) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: get the account matches the given id
    public Account getAccount(int id) {
        for (Account acc : workRoom.getAccounts()) {
            if (acc.getId() == id) {
                return acc;
            }
        }
        return null;
    }

    //REQUIRE: saving >= 0, chequing >= 0
    //MODIFIES: this
    //EFFECTS: take two parameters to make an account and add it to the account list
    private void register() {
        String name;
        int id;
        System.out.println("Type your name:");
        name = input.next();
        System.out.println("Create your user ID(numbers only): ");
        id = input.nextInt();

        Account acc = new Account(name, id, 0, 0);
        workRoom.addAccount(acc);
        System.out.println("Hi " + name + ", account has been created!");
        login();

    }

    //EFFECT: Generate random interest rate in the range 1.5% to 5%
    private double getInterestRate() {
        Random rand = new Random();
        double interestRate = 0.015 + rand.nextDouble() * 0.035;
        return interestRate;
    }
}
