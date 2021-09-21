package com.example.e1941319_mini_project.dto;

import com.example.e1941319_mini_project.StatusType;
import com.example.e1941319_mini_project.UserType;
import com.example.e1941319_mini_project.model.Package;

import java.io.Serializable;
import java.util.Arrays;

public class PackageViewDTO implements Serializable {
    private final Package packageData;
    private final UserType loginUserType;
    private final StatusType[] statusArray;

    public PackageViewDTO(Package packageData, UserType loginUserType, StatusType[] statusArray) {
        this.packageData = packageData;
        this.loginUserType = loginUserType;
        this.statusArray = statusArray;
    }

    public Package getPackageData() {
        return packageData;
    }

    public UserType getLoginUserType() {
        return loginUserType;
    }

    public StatusType[] getStatusArray() {
        return statusArray;
    }

    @Override
    public String toString() {
        return "PackageViewDTO{" +
                "packageData=" + packageData +
                ", loginUserType=" + loginUserType +
                ", statusArray=" + Arrays.toString(statusArray) +
                '}';
    }
}
