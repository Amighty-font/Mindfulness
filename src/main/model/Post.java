package model;

import org.json.JSONObject;
import persistence.Writable;

public class Post implements Writable {
    private Long postTime;
    private final String caption;

    //EFFECTS: creates a post that tracks post time and sets caption to given string
    //MODIFIES: this
    public Post(String post) {
        postTime = System.currentTimeMillis();
        caption = post;
    }

    //EFFECTS: returns post time of post
    public Long getPostTime() {
        return postTime;
    }

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
        return json;
    }
}
