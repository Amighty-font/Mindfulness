package ui;

import model.Account;
import model.AllAccounts;
import model.Posts;

import java.util.ArrayList;
import java.util.Scanner;

//EFFECTS: Mindful application
public class MindfulApp {
    private Account user;
    AllAccounts allAccounts = new AllAccounts();
    private Scanner input;

    //EFFECTS: runs MindfulApp
    public MindfulApp() {
        runMindfulApp();
    }

    //MODIFIES: this
    //EFFECTS: processes user inputs
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

    //MODIFIES: this
    //EFFECTS: gets user input for name and creates user account
    private void makeUser() {
        System.out.println("Input desired username");
        String name = input.nextLine();
        System.out.println("Your username is " + name);
        user = new Account(name);
        allAccounts.addAccount(user);
    }

    //EFFECTS: shows the menu
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

    //EFFECTS: takes input and processes commands
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

    //EFFECTS: prints followers, asks for interaction and returns given users posts
    private void getFollowers() {
        for (Account acc : user.getFollowers()) {
            System.out.println(acc.getName());
        }
        System.out.println("Would you like to interact with anyone? (y/n)");
        String yn = actualNextLine();
        if (yn.equals("y")) {
            System.out.println("Whos posts would you like to see?");
            String findName = actualNextLine();
            Account userFound = user.findFollowerUser(findName);
            if (userFound != null) {
                printPosts(userFound.getPosts());
            } else {
                System.out.println("Couldn't find user");
            }
        }
        noInput(yn);
    }

    private String actualNextLine() {
        String s = input.nextLine();
        if (s.isEmpty()) {
            s = input.nextLine();
        }
        return s;
    }

    //EFFECTS: prints all followers names and calls interaction method
    private void getFollowing() {
        for (Account acc : user.getFollowing()) {
            System.out.println(acc.getName());
        }
        System.out.println("Would you like to interact with anyone? (y/n)");
        interactWithFollowing();
    }

    //MODIFIES: user.following
    //EFFECTS asks for interaction, unfollows, or prints posts based on input
    private void interactWithFollowing() {
        String yn = actualNextLine();
        if (yn.equals("y")) {
            System.out.println("Who do you want to interact with?");
            String findName = actualNextLine();
            Account foundUser = user.findFollowingUser(findName);
            if (!(foundUser == null)) {
                followingOptions();
                String action = actualNextLine();
                if (action.equals("u")) {
                    user.unfollow(foundUser);
                } else if (action.equals("s")) {
                    printPosts(foundUser.getPosts());
                }
            } else {
                System.out.println("Couldn't find user");
                displayMenu();
            }
        } else {
            noInput(yn);
        }
    }

    //EFFECTS: prints the return for a "n" input
    private void noInput(String inp) {
        if (!inp.equals("n")) {
            System.out.println("not a valid input");
        }
        displayMenu();
    }

    //EFFECTS: prints following input menu
    private void followingOptions() {
        System.out.println("How would you like to interact with them?");
        System.out.println("u -> unfollow");
        System.out.println("s -> see posts");
    }

    //MODIFIES: user.following
    //EFFECTS: finds user with input name and adds to follow list, else does nothing
    private void follow() {
        System.out.println("Who would you like to follow");
        String findUser = actualNextLine();
        Account userFound = allAccounts.findByName(findUser);
        if (!(userFound == null)) {
            user.follow(userFound);
            System.out.println("Followed " + userFound.getName());
        } else {
            System.out.println("Couldn't find user");
        }
        displayMenu();
    }

    //MODIFIES: user.myPosts
    //EFFECTS: makes post with given string as caption and posts to user
    private void makePost() {
        System.out.println("What caption would you like to post?");
        String cap = actualNextLine();
        Posts post = new Posts(cap);
        user.makePost(post);
        System.out.println("posted with caption " + post.getCaption() + " at time: " + post.getPostTime());
        displayMenu();
    }

    //EFFECTS: prints previous posts
    private void viewPreviousPosts() {
        printPosts(user.getPosts());
        displayMenu();
    }

    //EFFECTS: goes through given arraylist and prints posts
    private void printPosts(ArrayList<Posts> post) {
        for (Posts p : post) {
            System.out.println(p.getCaption());
        }
    }

    //EFFECTS: calls printpost on users feed
    private void viewFeed() {
        printPosts(user.viewFeed());
    }

    //EFFECTS: initializes bot accounts and creates input var
    private void init() {
        Account influencer1 = new Account("Joe");
        Account influencer2 = new Account("Bob");
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
