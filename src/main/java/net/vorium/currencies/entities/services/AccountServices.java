package net.vorium.currencies.entities.services;

import net.vorium.currencies.Main;
import net.vorium.currencies.entities.Account;

import java.util.*;
import java.util.stream.Collectors;

public class AccountServices {

    private final Comparator<Account> COMPARATOR = Comparator.comparingDouble(Account::getBalance);

    private static final Map<String, Account> accounts = new HashMap<>();

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
                .sorted(COMPARATOR)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Collection<Account> getAccounts() {
        return accounts.values();
    }


}
