package com.example.a101.model;

import java.util.List;

public class Truck {
    private String truckname;
    private String description;
    private int truckid,image;

    public Truck(String truckname, String description) {
        this.truckname = truckname;
        this.description = description;
    }

    public Truck(String truckname, String description, int image) {
        this.truckname = truckname;
        this.description = description;
        this.image = image;

    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTruckname() {
        return truckname;
    }

    public void setTruckname(String truckname) {
        this.truckname = truckname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTruckid() {
        return truckid;
    }
}