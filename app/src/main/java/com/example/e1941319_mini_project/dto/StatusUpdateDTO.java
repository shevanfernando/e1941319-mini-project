package com.example.e1941319_mini_project.dto;

import com.example.e1941319_mini_project.StatusType;
import com.example.e1941319_mini_project.model.Status;

import java.util.List;

public class StatusUpdateDTO {
    private final String packageId;
    private final StatusType newStatus;
    private final String statusHistoryId;
    private final List<Status> statusList;

    public StatusUpdateDTO(String packageId, StatusType newStatus, String statusHistoryId, List<Status> statusList) {
        this.packageId = packageId;
        this.newStatus = newStatus;
        this.statusHistoryId = statusHistoryId;
        this.statusList = statusList;
    }

    public String getPackageId() {
        return packageId;
    }

    public StatusType getNewStatus() {
        return newStatus;
    }

    public String getStatusHistoryId() {
        return statusHistoryId;
    }

    public List<Status> getStatusList() {
        return statusList;
    }
}
