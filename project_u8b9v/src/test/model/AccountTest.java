package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account("John", 1, 20.0, 200.0);
    }

    @Test
    public void testDeposit() {
        account.deposit(50.0);
        assertEquals(250.0, account.getChequing());
        List<Entry> history = account.getHistory();
        assertEquals(1, history.size());
        Entry entry = history.get(0);
        assertEquals("deposit", entry.getType());
        assertEquals("chequing", entry.getFrom());
        assertEquals(50.0, entry.getAmount());
    }

    @Test
    public void testTransfer() {
        account.transfer(50.0);
        assertEquals(150.0, account.getChequing());
        assertEquals(70.0, account.getSaving());
        List<Entry> history = account.getHistory();
        assertEquals(1, history.size());
        account.transfer(250.0);
        assertEquals(150.0, account.getChequing());
        assertEquals(70.0, account.getSaving());
    }

    @Test
    public void testPrintSummary() {
        account.deposit(50.0);
        account.transfer(50.0);
        account.printSummary();
        // manually check output for correctness
    }

    @Test
    public void testCalculateRate() {
        double rate = account.calculateRate();
        assertEquals(true, rate >= 0.015 && rate <= 0.05);
    }

    @Test
    public void testCalculateScore() {
        account.deposit(1000.0);
        account.transfer(500.0);
        account.withdraw(200.0);
        int score = account.calculateScore();
        assertEquals(651, score);
    }

    @Test
    public void testGetId() {
        assertEquals(1,account.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("John", account.getName());
    }

    //    @Test
//    public void testWithdrawFromSavings() {
//        account.withdrawFromSavings(10.0);
//        assertEquals(150.0, account.getChequing());
//        assertEquals(10.0, account.getSaving());
//        List<Entry> history = account.getHistory();
//        assertEquals(1, history.size());
//        Entry entry = history.get(0);
//        assertEquals("withdraw", entry.getType());
//        assertEquals("saving", entry.getFrom());
//        assertEquals(50.0, entry.getAmount());
//    }
}
