package com.example.e1941319_mini_project.model;

import java.util.List;

public class Package {
    private final String packageId;
    private final String customerId;
    private final String deliveryAddress;
    private final String description;
    private final List<Status> status;

    public Package(String packageId, String customerId, String deliveryAddress, String description, List<Status> status) {
        this.packageId = packageId;
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.description = description;
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

    public List<Status> getStatus() {
        return status;
    }
}
