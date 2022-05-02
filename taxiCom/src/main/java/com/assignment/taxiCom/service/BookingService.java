package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Booking;
import com.assignment.taxiCom.repository.BookingRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BookingService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private BookingRepository bookingRepository;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public BookingRepository getBookingRepository() {
        return bookingRepository;
    }

    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public long addBooking(Booking booking){
        sessionFactory.getCurrentSession().saveOrUpdate(booking);
        return booking.getId();
    }

    public Booking updateBooking(Booking booking){
        sessionFactory.getCurrentSession().update(booking);
        return booking;
    }

    public long deleteBooking(Booking booking){
        sessionFactory.getCurrentSession().delete(booking);
        return booking.getId();
    }

    public Page<Booking> getAllBooking(int page, int pageSize){
        Page<Booking> bookings = bookingRepository.findAll(PageRequest.of(page, pageSize, Sort.by("id").ascending()));
        return bookings;
    }

    public Booking getBookingById(Long id){
        return sessionFactory.getCurrentSession().get(Booking.class, id);
    }

    public Booking getBookingByInvoiceId(Long invoiceId){
        return bookingRepository.findBookingByInvoiceId(invoiceId);
    }

    public Page<Booking> filterBookingByPickUpTime(LocalDateTime start, LocalDateTime end, int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("pickUpTime").ascending());
        Page<Booking> bookings = bookingRepository.filterBookingByPickUpTime(start, end, pageable);
        return bookings;
    }

    public Page<Booking> filterBookingByDropOffTime(LocalDateTime start, LocalDateTime end, int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("dropOffTime").ascending());
        Page<Booking> bookings = bookingRepository.filterBookingByDropOffTime(start, end, pageable);
        return bookings;
    }

    public Page<Booking> filterBookingByDistance(Double min, Double max, int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("distance").ascending());
        Page<Booking> bookings = bookingRepository.filterBookingByDistance(min, max, pageable);
        return bookings;
    }

    public Page<Booking> findBookingByStartLocation(String location, int page, int pageSize){
        location = "%" + location + "%";
        location = location.toUpperCase();
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Booking> bookings = bookingRepository.findBookingByStartLocation(location, pageable);
        return bookings;
    }

    public Page<Booking> findBookingByEndLocation(String location, int page, int pageSize){
        location = "%" + location + "%";
        location = location.toUpperCase();
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Booking> bookings = bookingRepository.findBookingByEndLocation(location, pageable);
        return bookings;
    }


}
