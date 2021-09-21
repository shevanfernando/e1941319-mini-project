package com.example.e1941319_mini_project.dto;

import com.example.e1941319_mini_project.UserType;

public class LoginResponseDTO {

    private final UserType userType;
    private final String customerId;
    private final Boolean status;

    public LoginResponseDTO(UserType userType, String customerId, Boolean status) {
        this.userType = userType;
        this.customerId = customerId;
        this.status = status;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Boolean getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "userType=" + userType +
                ", userId='" + customerId + '\'' +
                ", status=" + status +
                '}';
    }
}
