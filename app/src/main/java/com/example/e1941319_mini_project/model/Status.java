package com.example.e1941319_mini_project.model;

import com.example.e1941319_mini_project.StatusType;

import java.io.Serializable;

public class Status implements Serializable {

    private final String date;
    private final StatusType status;

    public Status(String date, StatusType status) {
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public StatusType getStatus() {
        return status;
    }
}
