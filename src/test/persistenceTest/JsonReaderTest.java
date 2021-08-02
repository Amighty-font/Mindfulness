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
            aa = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAllAccounts() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAllAccounts.json");
        try {
            aa = reader.read();
            assertEquals(0, aa.getAllAccounts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAllAccounts() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAllAccounts.json");
        try {
            aa = reader.read();
            List<Account> accounts = aa.getAllAccounts();
            assertEquals(3, accounts.size());
            checkAllAccounts(aa);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
