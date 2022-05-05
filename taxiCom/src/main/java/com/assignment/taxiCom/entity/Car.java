package com.assignment.taxiCom.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @CreationTimestamp
    private ZonedDateTime dateCreated;

    @Column(unique = true)
    private String vin;

    @Column
    private String make;

    @Column
    private String model;

    @Column
    private String color;

    @Column
    private boolean convertible;

    @Column
    private int rating;

    @Column
    private String licensePlate;

    @Column
    private int ratePerKilometer;

    @OneToOne(mappedBy = "car", fetch = FetchType.EAGER)
    private Driver driver;

    public Car() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isConvertible() {
        return convertible;
    }

    public void setConvertible(boolean convertible) {
        this.convertible = convertible;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getRatePerKilometer() {
        return ratePerKilometer;
    }

    public void setRatePerKilometer(int ratePerKilometer) {
        this.ratePerKilometer = ratePerKilometer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
