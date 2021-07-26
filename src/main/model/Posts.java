package model;

public class Posts {
    private Long postTime;
    private final String caption;

    public Posts(String post) {
        postTime = System.currentTimeMillis();
        caption = post;
    }

    public Long getPostTime() {
        return postTime;
    }

    public void setPostTime(Long milliTime) {
        postTime = milliTime;
    }

    public String getCaption() {
        return caption;
    }
}
