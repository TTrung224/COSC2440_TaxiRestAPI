package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Booking;
import com.assignment.taxiCom.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @RequestMapping(path = "/booking/filterPickUpDate/{strStartDate}/{strEndDate}", method = RequestMethod.GET)
    public Page<Booking> filterBookingByPickUpDate(@PathVariable String strStartDate, @PathVariable String strEndDate){
        LocalDateTime startDate = LocalDateTime.parse(strStartDate);
        LocalDateTime endDate = LocalDateTime.parse(strEndDate);
        return bookingService.filterBookingByPickUpDate(startDate, endDate);
    }

    @RequestMapping(path = "/booking/filterDropOffDate/{strStartDate}/{strEndDate}", method = RequestMethod.GET)
    public Page<Booking> filterBookingByDropOffDate(@PathVariable String strStartDate, @PathVariable String strEndDate){
        LocalDateTime startDate = LocalDateTime.parse(strStartDate);
        LocalDateTime endDate = LocalDateTime.parse(strEndDate);
        return bookingService.filterBookingByDropOffDate(startDate, endDate);
    }
}
