package com.example.e1941319_mini_project.dto;

import com.example.e1941319_mini_project.models.UserType;

public class LoginResponseDTO {

    private UserType userType;
    private Boolean status;

    public LoginResponseDTO(UserType userType, Boolean status) {
        this.userType = userType;
        this.status = status;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "userType=" + userType +
                ", status=" + status +
                '}';
    }
}
