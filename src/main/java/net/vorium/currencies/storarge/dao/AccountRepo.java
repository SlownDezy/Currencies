package net.vorium.currencies.storarge.dao;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import net.vorium.currencies.Main;
import net.vorium.currencies.entities.Account;
import net.vorium.currencies.entities.services.AccountServices;
import net.vorium.currencies.storarge.adapter.AccountAdapter;

import java.util.List;
import java.util.Set;

public class AccountRepo {

    private final SQLExecutor executor;
    private final String table = "currencies";

    public AccountRepo(Main plugin) {
        this.executor = new SQLExecutor(plugin.getConnector());
    }

    public void createTable() {
        executor.updateQuery(String.format(
                "CREATE TABLE IF NOT EXISTS %s " +
                "(username VARCHAR(16) NOT NULL PRIMARY KEY," +
                " coins DOUBLE NOT NULL);", table));
    }

    public Account findByName(String name) {
        return executor.resultOneQuery(String.format(
                "SELECT * FROM %s WHERE username = ?", table),
                statement -> statement.set(1, name),
                AccountAdapter.class);
    }

    public Set<Account> getAll() {
        return executor.resultManyQuery(String.format(
                "SELECT * FROM %s", table),
                statement -> {},
                AccountAdapter.class);
    }

    public void insertOne(Account account) {
        executor.updateQuery(String.format(
                "INSERT INTO %s VALUES(?,?)", table),
                statement -> {
                    statement.set(1, account.getName());
                    statement.set(2, account.getBalance());
                });
    }

    public void update(Account account) {
        executor.updateQuery(String.format(
                "REPLACE INTO %s VALUES(?, ?)", table),
                statment -> {
                    statment.set(1, account.getName());
                    statment.set(2, account.getBalance());
                });
    }
}
