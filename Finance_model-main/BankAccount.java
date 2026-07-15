package com.finance.domain;
import java.util.UUID;

public class BankAccount {
    protected String name;
    protected double balance;
    protected UUID id;

    public BankAccount(String name, double balance) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.balance = balance;
    }

    public void performTransaction(double amount, String type) {
        if (type.equals("INCOME")) {
            balance += amount;
            System.out.println("Поступление на счет " + name + ": " + amount);
        } else if (type.equals("EXPENSE") && balance >= amount) {
            balance -= amount;
            System.out.println("Списание со счета " + name + ": " + amount);
        } else {
            System.out.println("Недостаточно средств на счете " + name);
        }
    }

    public void checkBalance() {
        System.out.println("Счет " + name + " баланс: " + balance);
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[Счет: name=" + name + ", balance=" + balance + ", id=" + id + "]";
    }
}

