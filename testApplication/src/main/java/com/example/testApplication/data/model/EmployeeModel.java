package com.example.testApplication.data.model;

public class EmployeeModel {
    private float balance;
    private String name;
    private String message1;
    private String message2;

    public EmployeeModel(float balance, String name,String message1,String message2) {
        this.balance = balance;
        this.name = name;
        this.message1=message1;
        this.message2=message2;
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

    public String getName() {
        return name;
    }
}
