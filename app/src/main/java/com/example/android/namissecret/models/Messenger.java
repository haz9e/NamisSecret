package com.example.android.namissecret.models;

import java.io.Serializable;

public class Messenger implements Serializable {

    private String imageContact;
    private String username;
    private String age;

    public Messenger(String imageContact, String username, String age) {
        this.imageContact = imageContact;
        this.username = username;
        this.age = age;
    }


    public String getImageContact() {
        return imageContact;
    }

    public void setImageContact(String imageContact) {
        this.imageContact = imageContact;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getAge() { return age; }

    public void setAge(String age) {
        this.age = age;
    }

}