package com.domain;


import javax.validation.constraints.NotBlank;

public class Order {
    @NotBlank
    private String orderName;
    private String orderOwner;
    private String orderFile;
    private String orderDecision;
    private String orderExecutor;
    private String status;

    public Order() {

    }

    public Order(String orderName, String orderOwner, String orderExecutor) {
        this.orderName = orderName;
        this.orderOwner = orderOwner;
        this.orderExecutor = orderExecutor;
//        this.orderFile=orderFile;
    }


    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderOwner() {
        return orderOwner;
    }

    public void setOrderOwner(String orderOwner) {
        this.orderOwner = orderOwner;
    }

    public String getOrderFile() {
        return orderFile;
    }

    public void setOrderFile(String orderFile) {
        this.orderFile = orderFile;
    }

    public String getOrderDecision() {
        return orderDecision;
    }

    public void setOrderDecision(String orderDecision) {
        this.orderDecision = orderDecision;
    }

    public String getOrderExecutor() {
        return orderExecutor;
    }

    public void setOrderExecutor(String orderExecutor) {
        this.orderExecutor = orderExecutor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
