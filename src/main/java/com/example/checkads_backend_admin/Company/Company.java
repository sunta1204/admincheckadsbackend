package com.example.checkads_backend_admin.Company;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;
    private String phone;
    private String addresPhone;
    private Long userId;

    public  Company(){

    }

    public Company(String address, String phone, String addressPhone, Long userId) {
        this.address = address;
        this.phone = phone;
        this.addresPhone = addressPhone;
        this.userId = userId;
    }
    

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddresPhone() {
        return this.addresPhone;
    }

    public void setAddresPhone(String addresPhone) {
        this.addresPhone = addresPhone;
    }


    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
