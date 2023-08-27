package ui;


import model.Account;
import model.WorkRoom;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class GUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/workroom.json";
    private Account currentAccount;
    List<Account> accounts;
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel mainMenu;
    private JButton button1;
    private JButton button2;
    private JButton button5;
    private JButton button7;
    private JButton button6;

    private JPanel accountPanel;
    private JLabel info;

    private JPanel loginPage;
    private JButton login;
    private JTextField l1;
    private JLabel idl;

    private JPanel registerPage;
    private JButton register;
    private JTextField r1;
    private JTextField r2;
    private JLabel idr;
    private JLabel name;

    private JPanel userInputPanel;

    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JButton b8;
    private JButton b9;

    // Makes a new JFrame with different attributes
    public GUI() {
        super("Banking App");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        userInputPanel = new JPanel(new GridLayout(5, 1));
        workRoom = new WorkRoom("Main Workroom");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 500));
        initializeMenu();
        initializeMenuButtons();
        addTestUser();
        makeAccountPanel();
        makeLoginPanel();
        makeRegisterPanel();


        JLabel welcomeLabel = new JLabel("Welcome to U-Banking!");
        JLabel mainScreenImage = new JLabel();
        addLabel(welcomeLabel);
        addImageToLabel(mainScreenImage);



        addButtons(button1, button2, button5, button7, button6, mainMenu);

        addActionToButton1();
        addActionToButton2();

        mainMenu.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add a test user to bank
    private void addTestUser() {
        Account kai = new Account("Kai", 123, 5000,7000);
        workRoom.addAccount(kai);
    }


    // EFFECTS: Makes the main menu panel and changes the background color
    public void initializeMenu() {
        mainMenu = new JPanel();
        info = new JLabel("No Listings available");
        mainMenu.setBackground(Color.lightGray);
        add(mainMenu);

        //loadCarListings();
    }

    // EFFECTS: Initializes main menu buttons and gives them labels
    public void initializeMenuButtons() {
        button1 = new JButton("Login Account");
        button2 = new JButton("Register New Account");
        button5 = new JButton("Save Current File");
        button7 = new JButton("Load Last Save");
        button6 = new JButton("Exit application");
        b2 = new JButton("Withdraw");
        b3 = new JButton("Deposit");
        b4 = new JButton("Transfer");
        b5 = new JButton("Balance");
        b8 = new JButton("Transactions");
        b9 = new JButton("Return");
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to mainMenu
    public void addButton(JButton button1, JPanel panel) {
        button1.setFont(new Font("Arial", Font.BOLD, 12));
        button1.setBackground(Color.white);
        panel.add(button1);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: Calls the addButton method for each argument
    public void addButtons(JButton button1, JButton button2,
                           JButton button5, JButton button6, JButton button7,JPanel menu) {

        addButton(button1, menu);
        addButton(button2, menu);
        addButton(button5, menu);
        addButton(button6, menu);
        addButton(button7, menu);
    }

    // EFFECTS: Creates a button and adds it to the given panel, changing various attributes of the
    //          color and text of the button
    public void addMenuButton(JButton button1, JPanel panel) {
        button1.setFont(new Font("Arial", Font.BOLD, 12));
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.white);
        panel.add(button1);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: Creates the welcome text label and adds it to the main menu panel
    public void addLabel(JLabel j1) {
        j1.setFont(new Font("ComicSans", Font.BOLD, 50));
        mainMenu.add(j1);
    }

    // EFFECTS: Creates the image on the main menu and its it to the panel
    public void addImageToLabel(JLabel j1) {
        j1.setIcon(new ImageIcon("./data/bank.png"));
        j1.setMinimumSize(new Dimension(20,20));
        mainMenu.add(j1);
    }

    // MODIFIES: this
    // EFFECTS: Sets each button to their respective action
    public void addActionToButton1() {

        button1.addActionListener(this);
        button1.setActionCommand("Login Account");
        button2.addActionListener(this);
        button2.setActionCommand("Register New Account");
        button5.addActionListener(this);
        button5.setActionCommand("Save Current File");
        button6.addActionListener(this);
        button6.setActionCommand("Exit application");
        button7.addActionListener(this);
        button7.setActionCommand("Load Last Save");
    }

    // MODIFIES: this
    // EFFECTS: Sets each button to their respective action
    public void addActionToButton2() {

        b2.addActionListener(this);
        b2.setActionCommand("Withdraw");
        b3.addActionListener(this);
        b3.setActionCommand("Deposit");
        b5.addActionListener(this);
        b5.setActionCommand("Balance");
        b8.addActionListener(this);
        b8.setActionCommand("Transaction");
        b9.addActionListener(this);
        b9.setActionCommand("Return to main menu");
    }


    // EFFECTS: calls the given methods when a certain button is clicked on
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Login Account")) {
            initializeLoginPanel();
        } else if (ae.getActionCommand().equals("Register New Account")) {
            initializeRegisterPanel();
        } else if (ae.getActionCommand().equals("Login")) {
            login();
        } else if (ae.getActionCommand().equals("Register")) {
            addAccount();
        } else if (ae.getActionCommand().equals("Save Current File")) {
            saveFile();
        } else if (ae.getActionCommand().equals("Exit application")) {
            quit();
        } else if (ae.getActionCommand().equals("Return to main menu")) {
            returnToMainMenu();
        } else {
            action2(ae);
        }
    }

    public void action2(ActionEvent ae) {
        if (ae.getActionCommand().equals("Deposit")) {
            deposit();
        } else if (ae.getActionCommand().equals("Withdraw")) {
            withdraw();
        } else if (ae.getActionCommand().equals("Balance")) {
            showBalance();
        } else if (ae.getActionCommand().equals("Transaction")) {
            showTransaction();
        } else if (ae.getActionCommand().equals("Load Last Save")) {
            loadSave();
        }
    }


    // MODIFIES: this
    // EFFECTS: Creates the panel that displays the option for the user to input their car
    public void makeLoginPanel() {

        loginPage  = new JPanel(new GridLayout(0, 2));
        JButton mainMenuButton = new JButton("Return to Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.addActionListener(this);
        addMenuButton(mainMenuButton, loginPage);

        createLoginField();
        addLabelsToLogin();
    }

    // MODIFIES: this
    // EFFECTS: Creates the panel that displays the option for the user to input their car
    public void makeRegisterPanel() {

        registerPage  = new JPanel(new GridLayout(0, 2));
        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.addActionListener(this);
        addMenuButton(mainMenuButton, registerPage);

        createRegisterField();
        addLabelsToRegister();
    }

    // MODIFIES: this
    // EFFECTS: Creates the panel that displays the current account
    public void makeAccountPanel() {
        accountPanel = new JPanel();
        JLabel image = new JLabel();
        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.addActionListener(this);

        image.setIcon(new ImageIcon("./data/bank.png"));
        image.setMinimumSize(new Dimension(10,20));
        accountPanel.add(image);

        addButtons(b2,b3,b5,b8,b9,accountPanel);

        accountPanel.setVisible(true);
    }


    // EFFECTS: Adds the Login page to the screen, and sets the other ones false so they are not visible to the user
    public void initializeLoginPanel() {
        add(loginPage);
        loginPage.setVisible(true);
        mainMenu.setVisible(false);
        accountPanel.setVisible(false);
    }

    // EFFECTS: Adds the user input labels onto the panel
    public void addLabelsToLogin() {

        loginPage.add(login);
        loginPage.add(idl);
        loginPage.add(l1);
    }

    // EFFECTS: Adds the user input labels onto the panel
    public void addLabelsToRegister() {

        registerPage.add(register);
        registerPage.add(idr);
        registerPage.add(r1);
        registerPage.add(name);
        registerPage.add(r2);
    }

    // EFFECTS: Adds the Register page to the screen, and sets the other ones false
    public void initializeRegisterPanel() {
        add(registerPage);
        registerPage.setVisible(true);
        mainMenu.setVisible(false);
        accountPanel.setVisible(false);
    }

    // EFFECTS: Adds the Register page to the screen, and sets the other ones false
    public void initializeAccountPanel() {
        add(accountPanel);
        loginPage.setVisible(false);
        registerPage.setVisible(false);
        mainMenu.setVisible(false);
        accountPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Generates the fields for the user to type into
    public void createLoginField() {

        login = new JButton("Login");
        login.setActionCommand("Login");
        login.addActionListener(this);

        idl = new JLabel("Your ID:");
        l1 = new JTextField(10);

        listingLabelSettingsL();

    }

    // MODIFIES: this
    // EFFECTS: Generates the fields for the user to type into
    public void createRegisterField() {

        register = new JButton("Register");
        register.setActionCommand("Register");
        register.addActionListener(this);

        idr = new JLabel("Your ID:");
        r1 = new JTextField(10);

        name = new JLabel("Your Name:");
        r2 = new JTextField(10);

        listingLabelSettingsR();

    }

    // EFFECTS: Changes certain attributes of the labels and text fields
    private void listingLabelSettingsL() {

        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Arial", Font.BOLD, 12));

        idl.setFont(new Font("ComicSans", Font.BOLD, 24));
        l1.setMaximumSize(new Dimension(1200, 400));
    }


    // EFFECTS: Changes certain attributes of the labels and text fields
    private void listingLabelSettingsR() {

        register.setBackground(Color.BLACK);
        register.setForeground(Color.WHITE);
        register.setFont(new Font("Arial", Font.BOLD, 12));

        idr.setFont(new Font("ComicSans", Font.BOLD, 24));
        r1.setMaximumSize(new Dimension(1200, 400));
        name.setFont(new Font("ComicSans", Font.BOLD, 24));
        r2.setMaximumSize(new Dimension(1200, 400));
    }

    //EFFECTS: check if the account with the given id exists
    public boolean accountExist(int id) {
        accounts = workRoom.getAccounts();
        for (Account acc : accounts) {
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

    //EFFECTS: Login a user by prompting them for their user ID and opening a sub-menu
    public void login() {
        int id = Integer.parseInt(l1.getText());
        if (accountExist(id)) {
            currentAccount = getAccount(id);
            JOptionPane.showMessageDialog(null,"Welcome, " + currentAccount.getName() + "!");
            initializeAccountPanel();
        } else {
            JOptionPane.showMessageDialog(null,"Account does not exists!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds the user given listing into the Cars object to be displayed
    public void addAccount() {
        try {
            currentAccount = new Account(r2.getText(), Integer.parseInt(r1.getText()), 0, 0);
            workRoom.addAccount(currentAccount);
            JOptionPane.showMessageDialog(null, "Account Created!");
            initializeAccountPanel();

        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please try again");
        }
    }

    // EFFECTS: check balance button operation, shows balance of chequings and savings account as a pop-up message
    private void showBalance() {
        JOptionPane.showMessageDialog(null,
                "Your Chequing Account Balance is $" + currentAccount.getChequing() + "\n"
                   + "Your Saving Account Balance is $" + currentAccount.getSaving());
    }

    // EFFECTS: quit button operation, quits the atm and asks the options if you would like to save information
    private void quit() {
        int result = JOptionPane.showConfirmDialog(this,
                "Would you like to save your information?",
                "Save?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            saveFile();
        }
        printLog();
        System.exit(0);

    }

    // EFFECTS: Prints log to console once ATM is closed
    private void printLog() {
        System.out.println("Event Log:");
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString() + "\n");
        }
    }

    private void withdraw() {
        int amount = convertToInt(
                "Please enter the amount you wish to withdraw: ");
        if ((amount >= 0) && (amount <= currentAccount.getChequing())) {
            currentAccount.withdraw(amount);
            JOptionPane.showMessageDialog(null, "Withdrew Successful!");
        } else if ((amount > currentAccount.getChequing())) {
            JOptionPane.showMessageDialog(null, "Insufficient Funds!");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Please enter a valid amount. Please try again.");
        }
    }

    private void deposit() {
        int amount = convertToInt(
                "Please enter the amount you wish to deposit: ");
        if (amount >= 0) {
            currentAccount.deposit(amount);
            JOptionPane.showMessageDialog(null, "Deposit Successful!");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Please enter a valid amount. Please try again.");
        }
    }

    // REQUIRES: a non-zero length string that is an int
    // MODIFIES: this
    // EFFECTS: converts the input of numbers (string) to int
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // Lab 7: parse-conversion functions
    protected int convertToInt(String text) {
        String amountToConvert = JOptionPane.showInputDialog(userInputPanel, text, null);
        return Integer.parseInt(amountToConvert);
    }

    public void showTransaction() {
        JOptionPane.showMessageDialog(null, currentAccount.summary());
    }

    // MODIFIES: this
    // EFFECTS: loads the car listings from carlistings.txt file if it exists,
    // otherwise prints error
    private void loadSave() {
        try {
            workRoom = jsonReader.read();
            info.setText("Current Bank: \n" + workRoom.getName());
            JOptionPane.showMessageDialog(null, "Load Successful!");
        } catch (IOException e) {
            info.setText("No Listings added yet");
        } catch (IndexOutOfBoundsException e) {
            info.setText("Please initialize listings file before proceeding");
        }
    }

    // EFFECTS: saves state of the car listings with the user added listings
    // inspired by TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp
    private void saveFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(workRoom);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved Successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Unable to save banking session");
        }
    }

    // EFFECTS: Sets all panels' visibility to false except for the main menu
    public void returnToMainMenu() {
        mainMenu.setVisible(true);
        loginPage.setVisible(false);
        registerPage.setVisible(false);
        accountPanel.setVisible(false);
    }
}
