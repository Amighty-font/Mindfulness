package persistenceTest;

import model.Account;
import model.AllAccounts;
import model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @BeforeEach
    void writerSetUp() {
        setUp();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAllAccounts() {
        try {
            aa = new AllAccounts();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAllAccounts.json");
            writer.open();
            writer.write(aa);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAllAccounts.json");
            aa = reader.read();
            assertEquals(0, aa.getAllAccounts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterAllAccounts() {
        try {
            AllAccounts aa = new AllAccounts();
            JsonWriter writer = new JsonWriter("./data/testWriterAllAccounts.json");
            writer.open();
            writer.write(aa);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterAllAccounts.json");
            aa = reader.read();
            assertEquals(aa.getAllAccounts().size(), 0);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAllAccounts.json");
            writer.open();
            writer.write(aa);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAllAccounts.json");
            aa = reader.read();
            List<Account> accounts = aa.getAllAccounts();
            assertEquals(3, accounts.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
