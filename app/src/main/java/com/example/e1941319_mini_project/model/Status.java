package com.example.e1941319_mini_project.model;

public class Status {

    private final String date;
    private final String status;

    public Status(String date, String status) {
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
