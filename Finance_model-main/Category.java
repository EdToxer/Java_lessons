package com.finance.domain;

import java.util.UUID;

public class Category {
    protected String name;
    protected String type;
    protected UUID id;

    public Category(String name, String type) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.type = type;
    }

    public void describe() {
        if (type.equals("INCOME")) {
            System.out.println("Категория дохода: " + name);
        } else {
            System.out.println("Категория расхода: " + name);
        }
    }

    public boolean isIncome() {
        return type.equals("INCOME");
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[Категория: name=" + name + ", type=" + type + ", id=" + id + "]";
    }
}
