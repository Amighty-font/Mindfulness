package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account testAccount1;
    Account testAccount2;
    Account testAccount3;
    Posts testPost1;
    Posts testPost2;
    Posts testPost3;

    @BeforeEach
    public void setUp() {
        testAccount1 = new Account("one");
        testAccount2 = new Account("two");
        testAccount3 = new Account("three");
        testPost1 = new Posts("post one");
        testPost2 = new Posts("post two");
        testPost3 = new Posts("post three");
    }

    @Test
    public void testMakePost() {
        testAccount1.makePost(testPost1);
        assertEquals(testAccount1.getLastPost(), testPost1);
    }

    @Test
    public void testGetPosts() {
        testAccount1.makePost(testPost1);
        testAccount1.makePost(testPost2);
        testAccount1.makePost(testPost3);
        assertEquals(testAccount1.getLastPost(), testPost3);
    }

    @Test
    public void testFollowing() {
        testAccount2.follow(testAccount1);
        testAccount3.follow(testAccount1);
        testAccount2.follow(testAccount3);
        ArrayList<Account> testList1 = new ArrayList();
        testList1.add(testAccount2);
        testList1.add(testAccount3);
        assertEquals(testAccount1.getFollowers(), testList1);
        ArrayList<Account> testList2 = new ArrayList();
        testList2.add(testAccount1);
        testList2.add(testAccount3);
        assertEquals(testAccount2.getFollowing(), testList2);
        System.out.println(testPost1.getPostTime());
        System.out.println(testPost2.getPostTime());
        System.out.println(testPost3.getPostTime());
    }

    @Test
    public void testFeed() {
        testPost1.setPostTime(1000000000000L);
        testPost2.setPostTime(2000000000000L);
        testPost3.setPostTime(3000000000000L);
        testAccount3.follow(testAccount1);
        testAccount3.follow(testAccount2);
        testAccount1.makePost(testPost1);
        testAccount1.makePost(testPost3);
        testAccount2.makePost(testPost2);
        ArrayList<Posts> testList1 = new ArrayList();
        testList1.add(testPost1);
        testList1.add(testPost2);
        testList1.add(testPost3);
        assertEquals(testAccount3.viewFeed(), testList1);
    }

    @Test
    public void testFindUser() {
        testAccount1.follow(testAccount2);
        assertEquals(testAccount1.findFollowingUser("bobby"), null);
        assertEquals(testAccount2.findFollowerUser("bobby"), null);
        assertEquals(testAccount1.findFollowingUser("two"), testAccount2);
        assertEquals(testAccount2.findFollowerUser("one"), testAccount1);
    }
}