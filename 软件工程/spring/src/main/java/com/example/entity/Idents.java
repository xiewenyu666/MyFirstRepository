package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "idents")
public class Idents {

    @Id
    @Column(name = "iid")
    private int iid;

    @Column(name = "phone")
    private String phone;

    @Column(name = "recipientname")
    private String recipientname;

    @Column(name = "recipientphone")
    private String recipientphone;

    @Column(name = "book_name")
    private String book_name;

    @Column(name = "address")
    private String address;

    @Column(name = "price")
    private double price;

    @Column(name = "condition")
    private String condition;

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getrecipientname() {
        return recipientname;
    }

    public void setrecipientname(String recipientname) {
        this.recipientname = recipientname;
    }

    public String getRecipientphone() {
        return recipientphone;
    }

    public void setRecipientphone(String recipientphone) {
        this.recipientphone = recipientphone;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
