package ui;

import model.Account;
import model.AllAccounts;
import model.Post;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Gui implements ActionListener {

    private int frameWidth = 1002;
    private int frameHeight = 1500;

    //MainFrame
    Font font = new Font("Serif", Font.PLAIN, 25);
    Font fontHeader = new Font("Serif", Font.BOLD, 45);
    JFrame frame;
    JButton search;
    JTextField searchBar;

    //searchAction
    JLabel name;
    JLabel postsLab;
    JLabel followersLab;
    JLabel followingLab;
    JLabel postHeader;
    JLabel followerHeader;
    JLabel followingHeader;
    JButton followAction;
    Account interactingUser;
    Post interactingPost;
    JButton home;
    JButton quit;
    JButton logout;
    JButton makePost;
    JButton viewAccount;

    //Login GUI
    JPanel mainPanel;
    JLabel userName;
    JTextField userText;
    JTextField createPass;
    JLabel passLabel;
    JPasswordField passText;
    JButton loginButton;
    JButton newUser;
    JButton createAccount;
    JButton back;

    //MakePostGui
    JTextArea postCaption;
    JButton postButton;

    //MakeFeed
    JScrollPane scroll;
    JList feedList;

    //PlaySound
    Clip clip;
    AudioInputStream inputStream;


    //Class Variables
    private Account user;
    private AllAccounts allAccounts = new AllAccounts();

    //JSon variables
    private static final String JSON_STORE = "./data/AllAccounts.json";
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    public Gui() {
        //Initializes the main empty app
        frame = new JFrame("Mindful App");
        mainPanel = new JPanel();
        frame.setSize(frameWidth, frameHeight);
        mainPanel.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(null);

        init();
        loginGui();
    }

    private void loginGui() {
        resetGui();
        loginFonts();
        userName.setBounds(230, 675, 240, 35);
        mainPanel.add(userName);

        userText.setBounds(470, 675, 300, 35);
        mainPanel.add(userText);

        passLabel.setBounds(230, 735, 240, 35);
        mainPanel.add(passLabel);

        passText.setBounds(470, 735, 300, 35);
        mainPanel.add(passText);

        loginButton.setBounds(200, 795, 200, 35);
        loginButton.addActionListener(this);
        mainPanel.add(loginButton);


        newUser.setBounds(600, 795, 200, 35);
        newUser.addActionListener(this);
        mainPanel.add(newUser);

        mainPanel.setVisible(true);
        frame.setVisible(true);
    }

    private void loginFonts() {
        userName = new JLabel("Username");
        userName.setFont(font);
        userText = new JTextField(20);
        userText.setFont(font);
        passLabel = new JLabel("Password");
        passLabel.setFont(font);
        passText = new JPasswordField(20);
        passText.setFont(font);
        loginButton = new JButton("Login");
        loginButton.setFont(font);
        newUser = new JButton("New User");
        newUser.setFont(font);

    }

    public static void main(String[] args) {
        new Gui();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            searchButtonPress();
        } else if (e.getSource() == loginButton)  {
            String userInput = userText.getText();
            String passInput = passText.getText();
            loginCheck(userInput, passInput);
        } else if (e.getSource() == followAction) {
            if (followAction.getText().equals("Follow")) {
                followTone();
                user.follow(interactingUser);
                followAction.setText("Following");
            } else {
                user.unfollow(interactingUser);
                followAction.setText("Follow");
            }
            followersLab.setText(String.valueOf(interactingUser.getFollowers().size()));
        } else if (e.getSource() == quit) {
            saveAllAccounts();
            System.exit(0);
        } else {
            actionPerformedTwo(e);
        }
    }

    private void actionPerformedTwo(ActionEvent e) {
        if (e.getSource() == makePost) {
            makePostGui();
        }  else if (e.getSource() == logout) {
            frame.remove(scroll);
            loginGui();
        } else if (e.getSource() == home) {
            frame.remove(scroll);
            resetGui();
            setMainGui(user);
        } else if (e.getSource() == postButton) {
            interactingPost = new Post(postCaption.getText(), user);
            setMainGui(user);
        } else if (e.getSource() == viewAccount) {
            frame.remove(scroll);
            showAccount(user);
            createFeed(user.getPosts(),0, 350, frameWidth, frameHeight - 550);
            bottomBar();
            showAccountSetAndAdd();
        } else {
            actionPerformedThree(e);
        }
    }

    private void actionPerformedThree(ActionEvent e) {
        if (e.getSource() == createAccount) {
            String userInput = userText.getText();
            String passInput = createPass.getText();
            System.out.println(createPass);
            Account newAccount = new Account(userInput, passInput);
            allAccounts.addAccount(newAccount);
            setMainGui(newAccount);
        } else if (e.getSource() == newUser) {
            createAccount();
        } else if (e.getSource() == back) {
            loginGui();
        }
    }


    //EFFECTS: Check if login and password are correct
    private void loginCheck(String user, String pass) {
        Account foundUser = allAccounts.findByName(user);
        if (!(foundUser == null) && pass.equals(foundUser.getPassword())) {
            setMainGui(foundUser);
        } else {
            JLabel incorrectID = new JLabel("Incorrect username and/or password!");
            incorrectID.setBounds(300, 850, 500, 35);
            incorrectID.setFont(font);
            incorrectID.setBackground(Color.RED);
            loginGui();
            mainPanel.add(incorrectID);
            mainPanel.setVisible(true);
            frame.setVisible(true);
        }
    }


    private void setMainGui(Account receivedUser) {
        user = receivedUser;
        resetGui();

        searchBar = new JTextField(20);
        searchBar.setBounds(0, 0, frameWidth - 132, 100);
        searchBar.setFont(font);

        search = new JButton("Search");
        search.setBounds(frameWidth - 132, 0, 100, 100);
        search.addActionListener(this::actionPerformed);

        search.setFont(font);
        search.setText("Search");
        search.setVerticalTextPosition(SwingConstants.CENTER);
        search.setFocusable(false);

        bottomBar();
        createFeed(user.viewFeed(), 0, 100, frameWidth, frameHeight - 300);

        mainPanel.add(search);
        mainPanel.add(searchBar);
        //Intitializes frame and adds utilities to it
        frame.add(mainPanel);

        frame.setVisible(true);
    }


    private void createAccount() {
        resetGui();
        createAccountFont();

        userName.setBounds(230, 675, 240, 35);
        mainPanel.add(userName);

        passLabel.setBounds(230, 735, 240, 35);
        mainPanel.add(passLabel);

        userText = new JTextField(20);
        userText.setBounds(470, 675, 300, 35);
        userText.setFont(font);
        mainPanel.add(userText);

        createPass.setBounds(470, 735, 300, 35);
        mainPanel.add(createPass);

        createAccount.setBounds(600, 795, 200, 35);
        createAccount.addActionListener(this);
        mainPanel.add(createAccount);

        back.setBounds(200, 795, 200, 35);
        back.addActionListener(this);
        mainPanel.add(back);

        mainPanel.setVisible(true);
        frame.setVisible(true);
    }

    private void createAccountFont() {
        userName = new JLabel("Username");
        userName.setFont(font);
        passLabel = new JLabel("Password");
        passLabel.setFont(font);
        createPass = new JTextField(20);
        createPass.setFont(font);
        createAccount = new JButton("Create Account");
        createAccount.setFont(font);
        back = new JButton("Back");
        back.setFont(font);
    }

    private void makePostGui() {
        resetGui();
        frame.remove(scroll);
        postCaption = new JTextArea();
        postCaption.setBounds(0,0, frameWidth, frameHeight - 300);
        postCaption.setFont(font);
        mainPanel.add(postCaption);

        postButton = new JButton("Post");
        postButton.setBounds(726, frameHeight - 275, 200, 50);
        postButton.setFont(font);
        postButton.addActionListener(this);
        mainPanel.add(postButton);

        bottomBar();
    }

    private void bottomBar() {
        bottomBarFont();

        quit.setBounds(0, frameHeight - 200, 194, 112);
        logout.setBounds(194, frameHeight - 200, 194, 112);
        home.setBounds(388, frameHeight - 200, 194, 112);
        makePost.setBounds(582, frameHeight - 200, 194, 112);
        viewAccount.setBounds(776,frameHeight - 200, 194, 112);

        home.addActionListener(this);
        quit.addActionListener(this);
        logout.addActionListener(this);
        makePost.addActionListener(this);
        viewAccount.addActionListener(this);

        quit.setBackground(Color.RED);

        mainPanel.add(home);
        mainPanel.add(quit);
        mainPanel.add(logout);
        mainPanel.add(makePost);
        mainPanel.add(viewAccount);
    }

    private void bottomBarFont() {
        quit = new JButton("Quit");
        logout = new JButton("Logout");
        makePost = new JButton("Post");
        viewAccount = new JButton("Account");
        home = new JButton("Home");

        home.setFont(fontHeader);
        logout.setFont(fontHeader);
        quit.setFont(fontHeader);
        viewAccount.setFont(fontHeader);
        makePost.setFont(fontHeader);
    }

    private void searchButtonPress() {
        frame.remove(scroll);
        String searchInput = searchBar.getText();
        Account foundUser = allAccounts.findByName(searchInput);
        if (foundUser != null) {
            showAccount(foundUser);
            followActionButton(interactingUser);
            showAccountSetAndAdd();
            followAction.setFont(font);
            mainPanel.add(followAction);
            createFeed(foundUser.getPosts(),0, 450, frameWidth, frameHeight - 650);
            frame.add(mainPanel);
            bottomBar();
            frame.setVisible(true);
        }
    }

    //sets
    private void showAccount(Account acc) {
        resetGui();
        frame.remove(scroll);
        interactingUser = acc;
        name = new JLabel(acc.getName());
        postsLab = new JLabel(String.valueOf(acc.getPosts().size()));
        followersLab = new JLabel(String.valueOf(acc.getFollowers().size()));
        followingLab = new JLabel(String.valueOf(acc.getFollowing().size()));
        postHeader = new JLabel("Posts");
        followerHeader = new JLabel("Followers");
        followingHeader = new JLabel("Following");
        name.setBounds((frameWidth - 200) / 2, 80, 200, 100);

        postsLab.setBounds(100, 180, 200, 50);
        followersLab.setBounds(400, 180, 200, 50);
        followingLab.setBounds(700, 180, 200, 50);

        postHeader.setBounds(100, 250, 200, 50);
        followerHeader.setBounds(400, 250, 200, 50);
        followingHeader.setBounds(700, 250, 200, 50);
    }

    private void followActionButton(Account acc) {
        followAction = new JButton();
        if (user.getFollowing().contains(acc)) {
            followAction.setText("Following");
        } else {
            followAction.setText("Follow");
        }
        followAction.addActionListener(this);
        followAction.setBounds(100, 400, 800, 30);
    }

    private void createFeed(ArrayList<Post> posts, int x, int y, int width, int height) {
        feedList = new JList();
        feedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        feedList.setLayoutOrientation(JList.VERTICAL);
        feedList.setModel(createPosts(posts));
        feedList.setFont(font);

        scroll = new JScrollPane(feedList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(x, y, width, height);
        scroll.getViewport().setView(feedList);
        frame.add(scroll);
    }

    private DefaultListModel<String> createPosts(ArrayList<Post> posts) {
        DefaultListModel<String> stringPosts = new DefaultListModel<>();
        for (int k = posts.size() - 1; k >= 0; k--) {
            stringPosts.addElement(posts.get(k).getPostedBy().getName() + " : " + posts.get(k).getCaption());
        }
        return stringPosts;
    }

    private void showAccountSetAndAdd() {
        name.setFont(fontHeader);
        followersLab.setFont(fontHeader);
        followingLab.setFont(fontHeader);
        postsLab.setFont(fontHeader);
        postHeader.setFont(fontHeader);
        followerHeader.setFont(fontHeader);
        followingHeader.setFont(fontHeader);

        mainPanel.add(name);
        mainPanel.add(followersLab);
        mainPanel.add(followingLab);
        mainPanel.add(postsLab);
        mainPanel.add(postHeader);
        mainPanel.add(followerHeader);
        mainPanel.add(followingHeader);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void followTone() {
        try {
            clip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(new File("data/Follow Sound.wav"));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAllAccounts() {
        try {
            jsonWriter.open();
            jsonWriter.write(allAccounts);
            jsonWriter.close();
            System.out.println("Backed up all accounts to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadAllAccounts() {
        try {
            allAccounts = jsonReader.read();
            System.out.println("Loaded last backup from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void resetGui() {
        mainPanel.removeAll();
        frame.invalidate();
        frame.validate();
        frame.repaint();
        frame.getContentPane().invalidate();
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
    }

    private void init() {
        input = new Scanner(System.in);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        loadAllAccounts();
    }
}
