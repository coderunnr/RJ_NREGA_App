package com.android.rajasthanhackathon.models;

import java.io.Serializable;

/**
 * Created by root on 20/3/17.
 */
public class Employee implements Serializable{

    String Aadharno;
    String name;
    String bhamashah_ack_id;
    String naregano;
    String description;
    String photo;
    int id;
    int uploaded_by;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    String timestamp;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(int uploaded_by) {
        this.uploaded_by = uploaded_by;
    }

    public String getAadharno() {
        return Aadharno;
    }

    public void setAadharno(String aadharno) {
        Aadharno = aadharno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBhamashah_ack_id() {
        return bhamashah_ack_id;
    }

    public void setBhamashah_ack_id(String bhamashah_ack_id) {
        this.bhamashah_ack_id = bhamashah_ack_id;
    }

    public String getNaregano() {
        return naregano;
    }

    public void setNaregano(String naregano) {
        this.naregano = naregano;
    }
}
