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
            AllAccounts testEmpty = new AllAccounts();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAllAccounts.json");
            writer.open();
            writer.write(testEmpty);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAllAccounts.json");
            AllAccounts testAA = reader.read();
            assertEquals(0, testAA.getAllAccounts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterAllAccounts() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterAllAccounts.json");
            writer.open();
            writer.write(aa);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterAllAccounts.json");
            AllAccounts testAA = reader.read();
            checkAllAccounts(testAA);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
