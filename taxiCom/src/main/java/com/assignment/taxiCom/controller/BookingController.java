package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Booking;
import com.assignment.taxiCom.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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

    @PostMapping(path = "/bookings")
    public Object addBooking(@RequestBody Booking booking){
        return bookingService.addBooking(booking);
    }

    @PutMapping(path = "/bookings")
    public Booking updateBooking(@RequestBody Booking booking){
        return bookingService.updateBooking(booking);
    }

    @DeleteMapping(path = "/bookings")
    public Long deleteBooking(@RequestBody Booking booking){
        return bookingService.deleteBooking(booking);
    }

    @GetMapping(path = "/bookings")
    public Page<Booking> getAllBooking(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize){
        return bookingService.getAllBooking(page, pageSize);
    }

    @GetMapping(path = "/bookings/Id")
    public Booking getBookingById(@RequestParam long id){
        return bookingService.getBookingById(id);
    }

    @GetMapping(path = "/bookings/invoiceId")
    public Booking getBookingByInvoiceId(@RequestParam long invoiceId){
        return bookingService.getBookingByInvoiceId(invoiceId);
    }

    @GetMapping(path = "/bookings/createdTime/{strStart}/{strEnd}")
    public Page<Booking> filterBookingByCreatedTime(
            @PathVariable String strStart,
            @PathVariable String strEnd,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return bookingService.filterBookingByCreatedTime(strStart, strEnd, page, pageSize);
    }

    @GetMapping(path = "/bookings/pickUpTime/{strStart}/{strEnd}")
    public Page<Booking> filterBookingByPickUpTime(
            @PathVariable String strStart,
            @PathVariable String strEnd,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return bookingService.filterBookingByPickUpTime(strStart, strEnd, page, pageSize);
    }

    @GetMapping(path = "/bookings/dropOffTime/{strStart}/{strEnd}")
    public Page<Booking> filterBookingByDropOffTime(
            @PathVariable String strStart,
            @PathVariable String strEnd,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return bookingService.filterBookingByDropOffTime(strStart, strEnd, page, pageSize);
    }

    @GetMapping(path = "/bookings/distance/{min}/{max}")
    public Page<Booking> filterBookingByDistance(
            @PathVariable double min,
            @PathVariable double max,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return bookingService.filterBookingByDistance(min, max, page, pageSize);
    }

    @GetMapping(path = "/bookings/startLocation/{startLocation}")
    public Page<Booking> filterBookingByStartLocation(
            @PathVariable String startLocation,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return bookingService.findBookingByStartLocation(startLocation, page, pageSize);
    }

    @GetMapping(path = "/bookings/endLocation/{endLocation}")
    public Page<Booking> filterBookingByEndLocation(
            @PathVariable String endLocation,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return bookingService.findBookingByEndLocation(endLocation, page, pageSize);
    }
}