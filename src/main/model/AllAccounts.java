package model;

import java.util.ArrayList;

public class AllAccounts {
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
}
