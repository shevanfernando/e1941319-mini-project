package com.example.e1941319_mini_project.model;

import java.util.List;

public class Package {
    private final String packageId;
    private final String customerId;
    private final String deliveryAddress;
    private final String description;
    private final String currentStatus;
    private final List<Status> status;

    public Package(String packageId, String customerId, String deliveryAddress, String description, String currentStatus, List<Status> status) {
        this.packageId = packageId;
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.description = description;
        this.currentStatus = currentStatus;
        this.status = status;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public List<Status> getStatus() {
        return status;
    }
}
