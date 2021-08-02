package persistenceTest;

import model.Account;
import model.AllAccounts;
import model.Post;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    Account testAccount1;
    Account testAccount2;
    Account testAccount3;
    AllAccounts aa = new AllAccounts();
    Post testPost1;
    Post testPost2;
    Post testPost3;

    public void setUp() {
        testAccount1 = new Account("one");
        testAccount2 = new Account("two");
        testAccount3 = new Account("three");
        testPost1 = new Post("post one");
        testPost2 = new Post("post two");
        testPost3 = new Post("post three");
        testAccount1.makePost(testPost1);
        testAccount1.makePost(testPost1);
        testAccount2.follow(testAccount1);
        aa.addAccount(testAccount1);
        aa.addAccount(testAccount2);
        aa.addAccount(testAccount3);
    }

    public void checkAllAccounts(AllAccounts testAA) {
        assertEquals(testAA.getAllAccounts().size(), aa.getAllAccounts().size());
        for (Account acc: testAA.getAllAccounts()) {
            Account equalAcc = aa.findByName(acc.getName());
            assertEquals(equalAcc.getFollowers().size(), acc.getFollowers().size());
            assertEquals(equalAcc.getFollowing().size(), acc.getFollowing().size());
            assertEquals(equalAcc.getPosts().size(), acc.getPosts().size());
        }
    }
}
