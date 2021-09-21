package com.example.e1941319_mini_project.dto;

import androidx.annotation.NonNull;

import java.util.List;

public class FetchPackageDataDTO {
    private final List<String> packageIdList;
    private final List<PackageDTO> packageData;

    public FetchPackageDataDTO(List<String> packageIdList, List<PackageDTO> packageData) {
        this.packageIdList = packageIdList;
        this.packageData = packageData;
    }

    public List<String> getPackageIdList() {
        return packageIdList;
    }

    public List<PackageDTO> getPackageData() {
        return packageData;
    }

    @NonNull
    @Override
    public String toString() {
        return "FetchPackageDataDTO{" +
                "packageIdList=" + packageIdList +
                ", packageData=" + packageData +
                '}';
    }
}
