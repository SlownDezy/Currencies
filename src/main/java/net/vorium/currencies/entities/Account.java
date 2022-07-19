package net.vorium.currencies.entities;

import lombok.Data;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import net.vorium.currencies.Main;

import java.text.DecimalFormat;

@Data
public class Account {

    private String name;
    private double balance;

    private boolean toSync;

    public Account(String name) {
        this.name = name;
    }

    public void deposit(double amount) {
        balance += amount;
        toSync = true;
    }

    public void withdraw(double amount) {
        balance -= amount;
        if (balance <= 0) balance = 0;
        toSync = true;
    }

    public void set(double amount) {
        balance = amount;
        toSync = true;
    }

    public String getFormatedBalance() {
        return new DecimalFormat("#,###.#").format(balance);
    }

    public String getPrefix() {
        User user = Main.getInstance().getLuckPerms().getUserManager().getUser(name);

        if (user == null) return "";

        CachedMetaData metaData = user.getCachedData().getMetaData(QueryOptions.defaultContextualOptions());

        if (metaData.getPrefix() == null) return "";

        return metaData.getPrefix().replace("&", "§");
    }
}
