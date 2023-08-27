package persistence;

import model.Account;
import model.Entry;

import javax.print.attribute.Attribute;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    protected void checkAccount(Account account ,String name, int id, double saving, double chequing) {
        assertEquals(name, account.getName());
        assertEquals(id, account.getId());
    }
    protected void checkHistory (int i, ArrayList<Entry> actual, String type, String from, Double amount) {
        assertEquals(actual.get(i).getType(), type);
        assertEquals(actual.get(i).getFrom(), from);
        assertEquals(actual.get(i).getAmount(), amount);
    }
}
