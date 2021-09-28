package com.rm.jdbc.starter.entity;

import java.sql.Blob;

public class Aircraft {

    private Blob image;
    private Integer id;
    private String model;

    public Aircraft(Integer id) {
        this.id = id;
    }

    public Aircraft(Integer id, String model) {
        this.id = id;
        this.model = model;
    }

    public Aircraft(java.sql.Blob image, int id, String model) {
        this.image = image;
        this.id = id;
        this.model = model;
    }

    public Aircraft(Blob image, Integer id, String model) {
        this.image = image;
        this.id = id;
        this.model = model;
    }

    public java.sql.Blob getImage() {
        return image;
    }

    public void setImage(java.sql.Blob image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "image=" + image +
                ", id=" + id +
                ", model='" + model + '\'' +
                '}';
    }
}
