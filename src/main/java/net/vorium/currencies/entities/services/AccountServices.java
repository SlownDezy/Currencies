package net.vorium.currencies.entities.services;

import net.vorium.currencies.Main;
import net.vorium.currencies.entities.Account;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AccountServices {

    private static final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public AccountServices(Main plugin) {
        for (Account account : plugin.getRepository().getAll()) {
            registerAccount(account);
        }
    }

    public void registerAccount(Account account) {
        accounts.put(account.getName(), account);
    }

    public Account findByName(String name) {
        return accounts
                .values()
                .stream()
                .filter(account -> account.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Account> getRanking(int limit) {
        return accounts
                .values()
                .stream()
                .sorted(Comparator.comparing(Account::getBalance).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
    public Collection<Account> getAccounts() {
        return accounts.values();
    }
}
