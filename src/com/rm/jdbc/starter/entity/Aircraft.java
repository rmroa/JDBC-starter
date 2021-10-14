package com.rm.jdbc.starter.entity;

import java.sql.Blob;

public class Aircraft {

    private Integer id;
    private String model;
    private Blob image;

    public Aircraft(Integer id) {
        this.id = id;
    }

    public Aircraft(String model) {
        this.model = model;
    }

    public Aircraft(int id, String model, java.sql.Blob image) {
        this.id = id;
        this.model = model;
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

    public java.sql.Blob getImage() {
        return image;
    }

    public void setImage(java.sql.Blob image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", image=" + image +
                '}';
    }
}
