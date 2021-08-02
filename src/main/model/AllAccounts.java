package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class AllAccounts implements Writable {
    ArrayList<Account> allAccounts;

    public AllAccounts() {
        allAccounts = new ArrayList();
    }

    //EFFECTS: returns account by name in allAccounts, else returns null if can't find
    public Account findByName(String name) {
        for (Account acc : allAccounts) {
            if (acc.getName().equals(name)) {
                return acc;
            }
        }
        return null;
    }

    //EFFECTS: adds account to allAccounts
    //MODIFIES: this
    public void addAccount(Account acc) {
        allAccounts.add(acc);
    }


    //EFFECTS: returns arraylist of all accounts
    public ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("accounts", accountsToJson());

        return json;
    }

    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Account acc : allAccounts) {
            jsonArray.put(acc.toJson());
        }

        return jsonArray;
    }

}
