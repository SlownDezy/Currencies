package io.github.slowndezy.currencies.entities;

import lombok.Data;

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

}
