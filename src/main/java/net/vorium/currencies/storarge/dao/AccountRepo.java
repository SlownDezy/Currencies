package net.vorium.currencies.storarge.dao;

import net.vorium.currencies.Main;
import net.vorium.currencies.entities.Account;

import java.util.List;

public class AccountRepo {

    private final String table = "currencies";

    public AccountRepo(Main plugin) {
        //this.database = plugin.getProvider();
    }

    public void createTable() {
    }

    public Account findByName(String name) {
        return null;
    }

    public List<Account> getAll() {
        return null;
    }

    public void insertOne(Account account) {
    }
}
