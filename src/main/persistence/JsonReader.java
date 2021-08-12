package persistence;

import model.Account;
import model.AllAccounts;
import model.Post;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

//Citations :
// - https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads and returns workroom from file
    // throws IOException if cannot read data from file
    public AllAccounts read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAllAccounts(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: fills each empty account with the objects in jsonObject
    private void fillAccountDetail(JSONArray jsonAccounts, AllAccounts aa) {
        for (int k = 0; k < aa.getAllAccounts().size(); k++) {
            Account acc = aa.getAllAccounts().get(k);
            JSONObject jsonAccount = (JSONObject) jsonAccounts.get(k);
            JSONArray jsonFollowers = jsonAccount.getJSONArray("followers");
            JSONArray jsonFollowing = jsonAccount.getJSONArray("following");
            JSONArray jsonPosts = jsonAccount.getJSONArray("posts");
            fillFollAccounts(aa, acc, jsonFollowers, jsonFollowing, jsonPosts);
        }
    }

    //EFFECTS: finds and sets each
    private void fillFollAccounts(AllAccounts aa, Account acc, JSONArray jsonFollowers,
                                  JSONArray jsonFollowing, JSONArray jsonPosts) {
        for (Object id : jsonFollowing) {
            String name = (String) id;
            acc.follow(aa.findByName(name));
        }

        for (Object id : jsonPosts) {
            JSONObject jsonPost = (JSONObject) id;
            Long postTime = jsonPost.getLong("time");
            String postCaption = jsonPost.getString("caption");
            Account posts = aa.findByName(jsonPost.getString("postedBy"));
//            int likes = jsonPost.getInt("likes");
            Post post = new Post(postCaption, posts);
//            post.setLikes(likes);
            post.setPostTime(postTime);
//            acc.makePost(post);
        }
    }

    //EFFECTS: parses allAccounts from JSON object and returns it
    private AllAccounts parseAllAccounts(JSONObject jsonObject) {
        AllAccounts allAccounts = new AllAccounts();
        addAccounts(allAccounts, jsonObject);
        fillAccountDetail(jsonObject.getJSONArray("accounts"), allAccounts);
        return allAccounts;
    }

    //EFFECTS: parses accounts from JSON object and adds them to allAccounts
    private void addAccounts(AllAccounts aa, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("accounts");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addAccount(aa, nextAccount);
        }
    }

    //EFFECTS: parses account from JSON object and adds them to allAccounts
    private void addAccount(AllAccounts aa, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String pass = jsonObject.getString("password");
        Account acc = new Account(name, pass);
        aa.addAccount(acc);
    }
}
