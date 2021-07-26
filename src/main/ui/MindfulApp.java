package ui;

import model.Account;
import model.AllAccounts;
import model.Posts;

import java.util.ArrayList;
import java.util.Scanner;

public class MindfulApp {
    private Account user;
    AllAccounts allAccounts = new AllAccounts();
    private Account influencer1;
    private Account influencer2;
    private Scanner input;

    public MindfulApp() {
        runMindfulApp();
    }

    private void runMindfulApp() {
        boolean keepGoing = true;
        String command = null;

        init();
        makeUser();
        displayMenu();
        while (keepGoing) {
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    private void makeUser() {
        System.out.println("Input desired username");
        String name = input.nextLine();
        System.out.println("Your username is " + name);
        user = new Account(name);
        allAccounts.addAccount(user);
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tfe -> view followers");
        System.out.println("\tfi -> view following");
        System.out.println("\tv -> view feed");
        System.out.println("\tvpp -> view previous post");
        System.out.println("\tmp -> make post");
        System.out.println("\tfo -> follow");
        System.out.println("\tq -> quit");
    }

    private void processCommand(String command) {
        if (command.equals("fe")) {
            getFollowers();
        } else if (command.equals("fi")) {
            getFollowing();
        } else if (command.equals("v")) {
            viewFeed();
        } else if (command.equals("vpp")) {
            viewPreviousPosts();
        } else if (command.equals("mp")) {
            makePost();
        } else if (command.equals("fo")) {
            follow();
        }
    }

    private void getFollowers() {
        for (Account acc : user.getFollowers()) {
            System.out.println(acc.getName());
        }
        System.out.println("Would you like to interact with anyone? (y/n)");
        String yn = actualNextLine();
        if (yn.equals("y")) {
            System.out.println("Who would you like to interact with?");
            String findName = actualNextLine();
            for (Account acc : user.getFollowers()) {
                if (acc.getName() == findName) {
                    acc.getPosts();
                } else {
                    System.out.println("Couldn't find user");
                }
            }
        }
    }

    private String actualNextLine() {
        String s = input.nextLine();
        if (s.isEmpty()) {
            s = input.nextLine();
        }
        return s;
    }

    private void getFollowing() {
        for (Account acc : user.getFollowing()) {
            System.out.println(acc.getName());
        }
        System.out.println("Would you like to interact with anyone? (y/n)");
        String yn = actualNextLine();
        if (yn.equals("y")) {
            System.out.println("Who do you want to interact with?");
            String findName = actualNextLine();
            Account userFound = user.findFollowerUser(findName);
            if (userFound != null) {
                System.out.println("How would you like to interact with them?");
                System.out.println("u -> unfollow");
                System.out.println("s -> see their posts");
                String action = actualNextLine();

                if (action.equals("u")) {
                    user.unfollow(userFound);
                } else if (action.equals("s")) {
                    printPosts(userFound.getPosts());
                }
            } else {
                System.out.println("Couldn't find user");
            }
        }
    }

    private void follow() {
        System.out.println("Who would you like to follow");
        String findUser = actualNextLine();
        Account userFound = allAccounts.findByName(findUser);
        user.follow(userFound);
        System.out.println("Followed " + userFound.getName());

    }

    private void makePost() {
        System.out.println("What caption would you like to post?");
        String cap = actualNextLine();
        Posts post = new Posts(cap);
        user.makePost(post);
        System.out.println("posted with caption " + post.getCaption() + " at time: " + post.getPostTime());
    }

    private void viewPreviousPosts() {
        printPosts(user.getPosts());
    }

    private void printPosts(ArrayList<Posts> post) {
        for (Posts p : post) {
            System.out.println(p.getCaption());
        }
    }

    private void viewFeed() {
        printPosts(user.viewFeed());
    }

    private void init() {
        influencer1 = new Account("Joe");
        influencer2 = new Account("Bob");
        Posts post1 = new Posts("I am eating");
        Posts post2 = new Posts("I am sitting");
        Posts post3 = new Posts("I love food");
        influencer1.makePost(post1);
        influencer1.makePost(post2);
        influencer2.makePost(post3);
        allAccounts.addAccount(influencer1);
        allAccounts.addAccount(influencer2);
        input = new Scanner(System.in);
    }
}
