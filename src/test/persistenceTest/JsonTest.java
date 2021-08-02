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
        testAccount2.follow(testAccount1);
        aa.addAccount(testAccount1);
        aa.addAccount(testAccount2);
        aa.addAccount(testAccount3);
    }

    public void checkAllAccounts(AllAccounts testAA) {
        for (Account acc: testAA.getAllAccounts()) {
            Account equalAcc = aa.findByName(acc.getName());
            assertEquals(equalAcc.getFollowers(), acc.getFollowers());
            assertEquals(equalAcc.getFollowing(), acc.getFollowing());
            assertEquals(equalAcc.getPosts(), acc.getPosts());
        }
    }
}
