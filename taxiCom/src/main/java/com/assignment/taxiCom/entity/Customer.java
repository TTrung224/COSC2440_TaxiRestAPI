package com.assignment.taxiCom.entity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column(unique = true)
    private String phone;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss z")
    @Column
    private ZonedDateTime dateCreated;


    public Customer(){
        this.dateCreated = ZonedDateTime.now();
    }

    public Customer(long id, String name, String address, String phone, ZonedDateTime dateCreated) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dateCreated = dateCreated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}

