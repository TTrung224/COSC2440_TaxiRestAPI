package com.assignment.taxiCom.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name ="invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;




    @Column
    private int totalCharge;

    public Invoice(){};

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="customerID", referencedColumnName = "id",nullable = false)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="driverID", referencedColumnName = "id", nullable = false)
    private Driver driver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="bookingID", referencedColumnName = "id", nullable = false)
    private Booking booking;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(int totalCharge) {
        this.totalCharge = totalCharge;
    }
}
