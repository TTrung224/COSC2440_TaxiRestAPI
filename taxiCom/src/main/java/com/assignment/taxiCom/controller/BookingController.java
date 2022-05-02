package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Booking;
import com.assignment.taxiCom.service.BookingService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @PostMapping(path = "/booking")
    public long addBooking(@RequestBody Booking booking){
        return bookingService.addBooking(booking);
    }

    @PutMapping(path = "/booking")
    public Booking updateBooking(@RequestBody Booking booking){
        return bookingService.updateBooking(booking);
    }

    @DeleteMapping(path = "/booking")
    public Long deleteBooking(@RequestBody Booking booking){
        return bookingService.deleteBooking(booking);
    }

    @GetMapping(path = "/booking")
    public Page<Booking> getAllBooking(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize){
        System.out.println(bookingService.getAllBooking(page, pageSize));
        return bookingService.getAllBooking(page, pageSize);
    }

    @GetMapping(path = "/booking/searchById")
    public Booking getBookingById(@RequestParam long id){
        return bookingService.getBookingById(id);
    }

    @GetMapping(path = "/booking/searchByInvoiceId")
    public Booking getBookingByInvoiceId(@RequestParam long invoiceId){
        return bookingService.getBookingByInvoiceId(invoiceId);
    }

    @GetMapping(path = "/booking/filterByPickUpTime/{strStart}/{strEnd}")
    public Page<Booking> filterBookingByPickUpTime(
            @PathVariable String strStart,
            @PathVariable String strEnd,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        LocalDateTime start = LocalDateTime.parse(strStart);
        LocalDateTime end = LocalDateTime.parse(strEnd);
        return bookingService.filterBookingByPickUpTime(start, end, page, pageSize);
    }

    @GetMapping(path = "/booking/filterByDropOffTime/{strStart}/{strEnd}")
    public Page<Booking> filterBookingByDropOffTime(
            @PathVariable String strStart,
            @PathVariable String strEnd,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        LocalDateTime start = LocalDateTime.parse(strStart);
        LocalDateTime end = LocalDateTime.parse(strEnd);
        return bookingService.filterBookingByDropOffTime(start, end, page, pageSize);
    }

    @GetMapping(path = "/booking/filterByDistance/{min}/{max}")
    public Page<Booking> filterBookingByDistance(
            @PathVariable double min,
            @PathVariable double max,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return bookingService.filterBookingByDistance(min, max, page, pageSize);
    }

    @GetMapping(path = "/booking/filterByStartLocation/{startLocation}")
    public Page<Booking> filterBookingByStartLocation(
            @PathVariable String startLocation,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return bookingService.findBookingByStartLocation(startLocation, page, pageSize);
    }

    @GetMapping(path = "/booking/filterByEndLocation/{endLocation}")
    public Page<Booking> filterBookingByEndLocation(
            @PathVariable String endLocation,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
            ){

        return bookingService.findBookingByEndLocation(endLocation, page, pageSize);
    }
}