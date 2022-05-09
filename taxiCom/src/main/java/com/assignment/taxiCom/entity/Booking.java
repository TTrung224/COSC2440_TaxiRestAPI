package com.assignment.taxiCom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss z")
    @Column
    private final ZonedDateTime dateCreated;

    @Column
    private String startingLocation;

    @Column
    private String endLocation;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss z")
    @Column
    private ZonedDateTime pickUpTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss z")
    @Column
    private ZonedDateTime dropOffTime;

    @Column
    private double distance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoiceID", referencedColumnName = "id", unique = true)
    private Invoice invoice;

    public Booking(){
        ZonedDateTime time = ZonedDateTime.now();
        this.dateCreated = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public String getStartingLocation() {
        return startingLocation;
    }

    public void setStartingLocation(String startingLocation) {
        this.startingLocation = startingLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public ZonedDateTime getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(ZonedDateTime pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public ZonedDateTime getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(ZonedDateTime dropOffTime) {
        this.dropOffTime = dropOffTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
