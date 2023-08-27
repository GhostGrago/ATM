package persistence;

import model.Account;
import model.Entry;
import model.WorkRoom;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.numAccounts());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            wr.addAccount(new Account("saw", 1,123,123));
            wr.addAccount(new Account("needle", 2,234,234));
            Account acc1 = wr.getAccounts().get(0);
            ArrayList<Entry> his = new ArrayList<>();
            his.add(new Entry("withdraw", "chequing", 1.0));
            acc1.setHistory(his);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            List<Account> accs = wr.getAccounts();
            assertEquals(2, accs.size());
            checkAccount(accs.get(0), "saw", 1,123,123);
            checkAccount(accs.get(1), "needle", 2,234,234);
            checkHistory(0, (ArrayList<Entry>) accs.get(0).getHistory(),"withdraw", "chequing", 1.0);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}