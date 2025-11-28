package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        this.id = id;

        String[] parts = deliveryTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int min = Integer.parseInt(parts[1]);

        this.deliveryTime = hours*60 + min;

    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
