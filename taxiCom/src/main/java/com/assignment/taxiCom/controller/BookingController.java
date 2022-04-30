package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Booking;
import com.assignment.taxiCom.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    public BookingService getBookingService() {
        return bookingService;
    }

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(path = "/booking", method = RequestMethod.POST)
    public long addBooking(@RequestBody Booking booking){
        return bookingService.addBooking(booking);
    }

    @RequestMapping(path = "/booking", method = RequestMethod.GET)
    public Page<Booking> getAllBooking(){
        return bookingService.getAllBooking();
    }
}
