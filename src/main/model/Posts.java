package model;

public class Posts {
    private Long postTime;
    private final String caption;

    //EFFECTS: creates a post that tracks post time and sets caption to given string
    //MODIFIES: this
    public Posts(String post) {
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
}
