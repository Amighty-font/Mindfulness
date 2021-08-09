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
        testAccount1 = new Account("one", "pass");
        testAccount2 = new Account("two", "pass");
        testAccount3 = new Account("three", "pass");
        testPost1 = new Post("post one", testAccount1);
        testPost2 = new Post("post two", testAccount1);
        testPost3 = new Post("post three",testAccount2);
        testAccount2.follow(testAccount1);
        aa.addAccount(testAccount1);
        aa.addAccount(testAccount2);
        aa.addAccount(testAccount3);
    }

    public void checkAllAccounts(AllAccounts testAA) {
        assertEquals(testAA.getAllAccounts().size(), aa.getAllAccounts().size());
        System.out.println("size");
        for (Account acc: testAA.getAllAccounts()) {
            Account equalAcc = aa.findByName(acc.getName());
            assertEquals(equalAcc.getFollowers().size(), acc.getFollowers().size());
            System.out.println("followers");
            assertEquals(equalAcc.getFollowing().size(), acc.getFollowing().size());
            System.out.println("following");
            for (Post p : equalAcc.getPosts()) {
                System.out.println(p.getCaption());
            }
            System.out.println("acc posts");
            for (Post p : acc.getPosts()) {
                System.out.println(p.getCaption());
            }
            assertEquals(equalAcc.getPosts().size(), acc.getPosts().size());
            System.out.println("posts");
        }
    }
}
