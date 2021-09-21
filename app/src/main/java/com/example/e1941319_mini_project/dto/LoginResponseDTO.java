package com.example.e1941319_mini_project.dto;

import androidx.annotation.NonNull;

import com.example.e1941319_mini_project.UserType;

public class LoginResponseDTO {

    private final UserType userType;
    private final Boolean status;

    public LoginResponseDTO(UserType userType, Boolean status) {
        this.userType = userType;
        this.status = status;
    }

    public UserType getUserType() {
        return userType;
    }

    public Boolean getStatus() {
        return status;
    }

    @NonNull
    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "userType=" + userType +
                ", status=" + status +
                '}';
    }
}
