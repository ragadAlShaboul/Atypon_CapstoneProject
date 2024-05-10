package com.example.testApplication.data.model;

public class UserModel {
    private String name;
    private String message1;
    private String message2;
    private float balance;

    public UserModel(String name, String message1, String message2, float balance) {
        this.name = name;
        this.message1 = message1;
        this.message2 = message2;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getMessage1() {
        return message1;
    }

    public String getMessage2() {
        return message2;
    }

    public float getBalance() {
        return balance;
    }
}
