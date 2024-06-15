package com.example.entity;

import javax.persistence.*;

@Table(name = "admin")
public class Admin {

    @Id//主键
    @Column(name = "phone")
    private String phone;

    @Column(name = "password")//与表中的元素一一映射
    private String password;

    @Column(name = "name")//与表中的元素一一映射
    private String name;

    @Column(name = "priority")
    private int priority;

    @Column(name = "balance")
    private double balance;

    @Column(name = "vip_num")
    private Integer vip_num;

    @Column(name = "balance_sums")
    private double balance_sums;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getWhiteorblack() {
        return whiteorblack;
    }

    public void setWhiteorblack(int whiteorblack) {
        this.whiteorblack = whiteorblack;
    }

    @Column(name = "whiteorblack")
    private int whiteorblack;

    @Column(name = "img")
    private String img;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passward) {
        this.password = passward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Integer getVip_num() {
        return vip_num;
    }

    public void setVip_num(Integer vip_num) {
        this.vip_num = vip_num;
    }

    public double getBalance_sums() {
        return balance_sums;
    }

    public void setBalance_sums(double balance_sums) {
        this.balance_sums = balance_sums;
    }

}
