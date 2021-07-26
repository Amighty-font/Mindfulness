package model;

import java.util.ArrayList;
import java.util.Collections;

public class Account {
    private ArrayList<Account> following;
    private ArrayList<Account> followers;
    private ArrayList<Posts> myPosts;
    private ArrayList<Posts> saved;
    private String id;

    public Account(String name) {
        id = name;
        following = new ArrayList();
        followers = new ArrayList();
        myPosts = new ArrayList();
        saved = new ArrayList();

    }

    public String getName() {
        return id;
    }

    public ArrayList<Posts> getPosts() {
        return myPosts;
    }

    public void makePost(Posts post) {
        myPosts.add(post);
    }

    public void follow(Account account) {
        following.add(account);
        account.followers.add(this);
    }

    public void unfollow(Account account) {
        following.remove(account);
    }

    public ArrayList<Account> getFollowers() {
        return followers;
    }

    public ArrayList<Account> getFollowing() {
        return following;
    }

    public Posts getLastPost() {
        return myPosts.get(myPosts.size() - 1);
    }

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

    public Account findFollowingUser(String name) {
        for (Account acc : following) {
            if (acc.getName() == name) {
                return acc;
            }
        }
        return null;
    }

    public Account findFollowerUser(String name) {
        for (Account acc : followers) {
            if (acc.getName() == name) {
                return acc;
            }
        }
        return null;
    }

    private static void quickSortByTime(ArrayList<Posts> posts) {
        quickSortByTime(posts, 0, posts.size() - 1);
    }

    private static void quickSortByTime(ArrayList<Posts> posts, int start, int end) {
        if (start >= end) {  //base case of when it is fully partitioned
            return;
        }
        int index = partition(posts, start, end);
        quickSortByTime(posts, start, index - 1);  //sort left side of array
        quickSortByTime(posts, index + 1, end);   //sort right side of array
    }

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

    private static void swapPosts(ArrayList<Posts> posts, int indexA, int indexB) {
        Posts temp = posts.get(indexA);
        posts.set(indexA, posts.get(indexB));
        posts.set(indexB, temp);
    }
}
