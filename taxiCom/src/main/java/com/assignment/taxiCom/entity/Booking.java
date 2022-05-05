package com.assignment.taxiCom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;


@Entity
@Table(name = "booking")
public class Booking {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column
        private ZonedDateTime dateCreated;

        @Column
        private String startingLocation;

        @Column
        private String endLocation;

        @Column
        private LocalDateTime pickUpTime;

        @Column
        private LocalDateTime dropOffTime;

        @Column
        private double distance;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="bookingID", referencedColumnName = "id", nullable = false)
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

        public void setDateCreated(ZonedDateTime dateCreated) {
            this.dateCreated = dateCreated;
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

        public LocalDateTime getPickUpTime() {
            return pickUpTime;
        }

        public void setPickUpTime(String strPickUpTime) {
            LocalDateTime pickUpTime = LocalDateTime.parse(strPickUpTime);
            this.pickUpTime = pickUpTime;
        }

        public LocalDateTime getDropOffTime() {
            return dropOffTime;
        }

        public void setDropOffTime(String strDropOffTime) {
            LocalDateTime dropOffTime = LocalDateTime.parse(strDropOffTime);
            this.dropOffTime = dropOffTime;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public Invoice getInvoice() {
            return (Invoice) invoice;
        }

        public void setInvoice(Invoice invoice) {
            this.invoice = invoice;
        }

}
