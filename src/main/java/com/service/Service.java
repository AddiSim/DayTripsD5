package com.service;

public interface Service {
    String getServiceName();
    void setServiceName(String serviceName);

    Double getServicePrice();
    void setServicePrice(Double servicePrice);

    String getServiceDescription();
    void setServiceDescription(String serviceDescription);
}
