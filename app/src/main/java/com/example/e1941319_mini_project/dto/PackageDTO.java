package com.example.e1941319_mini_project.dto;

import com.example.e1941319_mini_project.StatusType;

public class PackageDTO {
    private final String customerId;
    private final String deliveryAddress;
    private final String description;
    private final StatusType currentStatus;

    public PackageDTO(String customerId, String deliveryAddress, String description, StatusType currentStatus) {
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.description = description;
        this.currentStatus = currentStatus;
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

    public StatusType getCurrentStatus() {
        return currentStatus;
    }
}
