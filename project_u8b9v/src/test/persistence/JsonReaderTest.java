package persistence;

import model.Account;
import model.AccountTest;
import model.Entry;
import model.WorkRoom;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WorkRoom wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            WorkRoom wr = reader.read();
            assertEquals("Main Workroom", wr.getName());
            assertEquals(0, wr.numAccounts());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            WorkRoom wr = reader.read();
            assertEquals("Main Workroom", wr.getName());
            List<Account> accounts = wr.getAccounts();
            assertEquals(2, accounts.size());
            checkAccount(accounts.get(0),"Kai", 1, 2343,12);
            checkAccount(accounts.get(1),"James", 2, 123,32 );
            checkHistory(0, (ArrayList<Entry>) accounts.get(0).getHistory(),"withdraw", "chequing", 1.0);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}