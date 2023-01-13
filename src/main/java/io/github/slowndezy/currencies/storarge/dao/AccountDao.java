package io.github.slowndezy.currencies.storarge.dao;

import com.google.common.collect.Sets;
import io.github.slowndezy.currencies.entities.Account;
import io.github.slowndezy.currencies.storarge.Database;
import io.github.slowndezy.currencies.CurrenciesPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class AccountDao {

    private final Database database;

    public AccountDao(CurrenciesPlugin plugin) {
        this.database = plugin.getDatabase();
    }

    public void createTable() {
        try (Connection connection = database.getDataSource().getConnection()) {
            connection.prepareStatement(String.format(
                    "CREATE TABLE IF NOT EXISTS %s (username VARCHAR(16) NOT NULL PRIMARY KEY, coins DOUBLE NOT NULL);", database.getTable()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<Account> findByName(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = database.getDataSource().getConnection()) {
                PreparedStatement statement = connection.prepareStatement(String.format(
                        "SELECT * FROM %s WHERE username = ?", database.getTable()));
                statement.setString(1, name);

                ResultSet resultSet = statement.executeQuery();
                Account account = new Account(resultSet.getString("username"));
                account.setBalance(resultSet.getDouble("coins"));
                return account;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public CompletableFuture<Set<Account>> getAll() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = database.getDataSource().getConnection()) {
                PreparedStatement statement = connection.prepareStatement(String.format(
                        "SELECT * FROM %s", database.getTable()));

                ResultSet resultSet = statement.executeQuery();
                Set<Account> accounts = Sets.newHashSet();

                while (resultSet.next()) {
                    Account account = new Account(resultSet.getString("username"));
                    account.setBalance(resultSet.getDouble("coins"));
                    accounts.add(account);
                }

                return accounts;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public CompletableFuture<Void> insert(Account account) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = database.getDataSource().getConnection()) {
                PreparedStatement statement = connection.prepareStatement(String.format(
                        "INSERT INTO %s VALUES(?,?)", database.getTable()));
                statement.setString(1, account.getName());
                statement.setDouble(2, account.getBalance());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public CompletableFuture<Void> update(Account account) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = database.getDataSource().getConnection()) {
                PreparedStatement statement = connection.prepareStatement(String.format(
                        "REPLACE INTO %s VALUES(?, ?)", database.getTable()));
                statement.setString(1, account.getName());
                statement.setDouble(2, account.getBalance());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
