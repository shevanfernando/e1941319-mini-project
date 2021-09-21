package com.example.e1941319_mini_project.dto;

public class PackageDTO {

    private String packageId;
    private final String customerId;
    private final String deliveryAddress;
    private final String description;

    public PackageDTO(String customerId, String deliveryAddress, String description) {
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.description = description;
    }

    public PackageDTO(String packageId, String customerId, String deliveryAddress, String description) {
        this.packageId = packageId;
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.description = description;
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

    @Override
    public String toString() {
        return "PackageDTO{" +
                "packageId='" + packageId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
