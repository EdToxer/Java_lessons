package com.finance.domain;

import java.time.LocalDate;
import java.util.UUID;

public class Operation {
    protected String type;
    protected double amount;
    protected LocalDate date;
    protected String description;
    protected UUID bankAccountId;
    protected UUID categoryId;
    protected UUID id;

    public Operation(String type, double amount, LocalDate date, String description,
                     UUID bankAccountId, UUID categoryId) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.bankAccountId = bankAccountId;
        this.categoryId = categoryId;
    }

    public void execute(BankAccount account, Category category) {
        if (type.equals("INCOME") && category.isIncome()) {
            account.performTransaction(amount, "INCOME");
            System.out.println("Операция: " + description + " (категория: " + category.getName() + ")");
        } else if (type.equals("EXPENSE") && !category.isIncome()) {
            account.performTransaction(amount, "EXPENSE");
            System.out.println("Операция: " + description + " (категория: " + category.getName() + ")");
        } else {
            System.out.println("Ошибка: несоответствие типа операции и категории");
        }
    }

    public void showInfo() {
        System.out.println(type + ": " + amount + " (" + date + ") - " + description);
    }

    @Override
    public String toString() {
        return "[Операция: type=" + type + ", amount=" + amount +
                ", date=" + date + ", description=" + description + "]";
    }
}
