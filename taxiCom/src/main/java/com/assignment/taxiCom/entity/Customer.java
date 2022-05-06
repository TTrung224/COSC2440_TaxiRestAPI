package com.assignment.taxiCom.entity;
import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private int phone;
    @Column
    private ZonedDateTime DateCreated;


    public Customer(){

    }

    public Customer(int id, String name, String address, int phone, ZonedDateTime DateCreated) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.DateCreated = DateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public ZonedDateTime getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        DateCreated = dateCreated;
    }
}


