package com.example.e1941319_mini_project.dto;

public class PackageDTO {

    private String packageId;
    private String customerId;
    private String deliveryAddress;
    private String description;

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

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
