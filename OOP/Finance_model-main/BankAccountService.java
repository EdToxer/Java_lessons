package com.finance.domain;

import java.util.ArrayList;
import java.util.List;

public class BankAccountService {
    private List<BankAccount> accounts = new ArrayList<>();

    public BankAccount createAccount(String name, double balance) {
        BankAccount account = new BankAccount(name, balance);
        accounts.add(account);
        System.out.println("Добавлен новый счет: " + account);
        return account;
    }

    public BankAccount findAccountByName(String name) {
        return accounts.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<BankAccount> getAllAccounts() {
        return accounts;
    }
}
