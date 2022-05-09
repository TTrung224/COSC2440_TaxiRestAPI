package com.assignment.taxiCom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private double totalCharge;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss z")
    @Column
    private ZonedDateTime dateCreated;

    public Invoice(){
        ZonedDateTime now = ZonedDateTime.now();
        this.dateCreated = now;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="customerID", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="driverID", referencedColumnName = "id")
    private Driver driver;

    @OneToOne(mappedBy = "invoice")
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


    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(int totalCharge) {
        this.totalCharge = totalCharge;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
