package model;

import org.json.JSONObject;
import persistence.Writable;

public class Post implements Writable {
    private Long postTime;
    private final String caption;
//    private int likes;
    private Account postedBy;

    //EFFECTS: creates a post that tracks post time and sets caption to given string
    //MODIFIES: this
    public Post(String post, Account account) {
        postTime = System.currentTimeMillis();
        caption = post;
        postedBy = account;
        account.makePost(this);
    }

    //EFFECTS: returns post time of post
    public Long getPostTime() {
        return postTime;
    }

//    public void setLikes(int likes) {
//        this.likes = likes;
//    }

    public Account getPostedBy() {
        return postedBy;
    }
//
//    public void addLike() {
//        likes++;
//    }

    //EFFECTS: sets post time of a post
    public void setPostTime(Long milliTime) {
        postTime = milliTime;
    }

    //EFFECT: returns caption of a post
    public String getCaption() {
        return caption;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("time", postTime);
        json.put("caption", caption);
//        json.put("likes", likes);
        json.put("postedBy", postedBy.getName());
        return json;
    }
}
