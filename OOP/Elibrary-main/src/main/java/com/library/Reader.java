package com.library;

import java.sql.Date;

public class Reader {
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private Date registeredDate;

    public Reader() {}

    public Reader(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public void setRegisteredDate(Date registeredDate) { this.registeredDate = registeredDate; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | %s | %s | Зарегистрирован: %s",
                id, fullName, email, phone, registeredDate);
    }
}