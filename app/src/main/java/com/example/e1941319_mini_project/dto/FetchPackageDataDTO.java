package com.example.e1941319_mini_project.dto;

import com.example.e1941319_mini_project.model.Package;

import java.util.List;

public class FetchPackageDataDTO {
    private final List<String> packageIdList;
    private final List<Package> packageData;

    public FetchPackageDataDTO(List<String> packageIdList, List<Package> packageData) {
        this.packageIdList = packageIdList;
        this.packageData = packageData;
    }

    public List<String> getPackageIdList() {
        return packageIdList;
    }

    public List<Package> getPackageData() {
        return packageData;
    }

    @Override
    public String toString() {
        return "FetchPackageDataDTO{" +
                "packageIdList=" + packageIdList +
                ", packageData=" + packageData +
                '}';
    }
}
