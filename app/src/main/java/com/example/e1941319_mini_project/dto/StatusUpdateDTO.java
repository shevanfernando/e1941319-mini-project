package com.example.e1941319_mini_project.dto;

import com.example.e1941319_mini_project.StatusType;

public class StatusUpdateDTO {
    private final String packageId;
    private final StatusType status;
    private final String statusHistoryId;

    public StatusUpdateDTO(String packageId, StatusType status, String statusHistoryId) {
        this.packageId = packageId;
        this.status = status;
        this.statusHistoryId = statusHistoryId;
    }

    public String getPackageId() {
        return packageId;
    }

    public StatusType getStatus() {
        return status;
    }

    public String getStatusHistoryId() {
        return statusHistoryId;
    }
}
