package model;

import java.util.ArrayList;
import java.util.Collections;

public class Account {
    private ArrayList<Account> following;
    private ArrayList<Account> followers;
    private ArrayList<Posts> myPosts;
    private ArrayList<Posts> saved;
    private String id;

    //EFFECTS: creates account with name as the given string, following, followers, posts and saved list
    public Account(String name) {
        id = name;
        following = new ArrayList();
        followers = new ArrayList();
        myPosts = new ArrayList();
        saved = new ArrayList();
    }

    //EFFECT: return name of account
    public String getName() {
        return id;
    }

    //EFFECT: return posts a user has made
    public ArrayList<Posts> getPosts() {
        return myPosts;
    }

    //EFFECTS: takes a post and adds it to the myPosts of an account
    //MODIFIES: this
    public void makePost(Posts post) {
        myPosts.add(post);
    }

    //EFFECT: adds an account to following
    //MODIFIES: this
    public void follow(Account account) {
        following.add(account);
        account.followers.add(this);
    }

    //EFFECT: removes an account from following
    //MODIFIES: this
    public void unfollow(Account account) {
        following.remove(account);
    }

    //EFFECT: returns followers list
    public ArrayList<Account> getFollowers() {
        return followers;
    }

    //EFFECT: return following list
    public ArrayList<Account> getFollowing() {
        return following;
    }

    //EFFECT: returns the most recent post
    public Posts getLastPost() {
        if (!myPosts.isEmpty()) {
            return myPosts.get(myPosts.size() - 1);
        } else {
            return null;
        }
    }

    //EFFECT: return a sorted list of posts based on time
    public ArrayList<Posts> viewFeed() {
        ArrayList<Posts> fullFeed = new ArrayList();
        for (Account a : following) {
            for (Posts p : a.getPosts()) {
                fullFeed.add(p);
            }
        }

        quickSortByTime(fullFeed);
        return fullFeed;
    }

    //EFFECT: returns a user with same given string in following list, else returns null if not found
    public Account findFollowingUser(String name) {
        for (Account acc : following) {
            if (acc.getName().equals(name)) {
                return acc;
            }
        }
        return null;
    }

    //EFFECT: returns a user with same given string in followers list, else returns null if not found
    public Account findFollowerUser(String name) {
        for (Account acc : followers) {
            if (acc.getName().equals(name)) {
                return acc;
            }
        }
        return null;
    }

    //EFFECT: sorts given array of posts by post time
    private static void quickSortByTime(ArrayList<Posts> posts) {
        quickSortByTime(posts, 0, posts.size() - 1);
    }

    //EFFECT: sorts left and right side of array until start>=end, ie. the list is sorted
    private static void quickSortByTime(ArrayList<Posts> posts, int start, int end) {
        if (start >= end) {  //base case of when it is fully partitioned
            return;
        }
        int index = partition(posts, start, end);
        quickSortByTime(posts, start, index - 1);  //sort left side of array
        quickSortByTime(posts, index + 1, end);   //sort right side of array
    }

    //EFFECT: compares pivotValue and elements of the array from start to end and swaps if posttime < pivotIndex
    private static int partition(ArrayList<Posts> posts, int start, int end) {
        int pivotIndex = start;
        Long pivotValue = posts.get(end).getPostTime();
        for (int i = start; i < end; i++) {
            if (posts.get(i).getPostTime() < pivotValue) {
                swapPosts(posts, i, pivotIndex);
                pivotIndex++;
            }
        }
        swapPosts(posts, pivotIndex, end);
        return pivotIndex;
    }

    //EFFECT swaps post at indexA with post at indexB
    private static void swapPosts(ArrayList<Posts> posts, int indexA, int indexB) {
        Posts temp = posts.get(indexA);
        posts.set(indexA, posts.get(indexB));
        posts.set(indexB, temp);
    }
}
