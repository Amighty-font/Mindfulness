package persistenceTest;

import model.Account;
import model.AllAccounts;
import model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @BeforeEach
    void readerSetUp() {
        setUp();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AllAccounts testAA = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAllAccounts() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAllAccounts.json");
        try {
            AllAccounts testAA = reader.read();
            assertEquals(0, testAA.getAllAccounts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderAllAccounts() {
        JsonReader reader = new JsonReader("./data/testReaderAllAccounts.json");
        try {
            AllAccounts testAA = reader.read();
            checkAllAccounts(testAA);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
