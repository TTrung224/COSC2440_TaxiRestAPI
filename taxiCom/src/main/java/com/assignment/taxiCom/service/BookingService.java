package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Booking;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.SimpleTimeZone;

@Service
@Transactional
public class BookingService {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long addBooking(Booking booking){
        sessionFactory.getCurrentSession().saveOrUpdate(booking);
        return booking.getId();
    }

    public Page<Booking> getAllBooking(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        Page<Booking> page = new PageImpl<>(criteria.list());
        return page;
    }

    public Booking getBookingById(Long id){
        return sessionFactory.getCurrentSession().get(Booking.class, id);
    }

    public Page<Booking> filterBookingByPickUpDate(LocalDateTime start, LocalDateTime end){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        criteria.add(Restrictions.ge("pickUpTime", start));
        criteria.add(Restrictions.le("pickUpTime", end));
        Page<Booking> page = new PageImpl<>(criteria.list());
        return page;
    }

    public Page<Booking> filterBookingByDropOffDate(LocalDateTime start, LocalDateTime end){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        criteria.add(Restrictions.ge("dropOffTime", start));
        criteria.add(Restrictions.le("dropOffTime", end));
        Page<Booking> page = new PageImpl<>(criteria.list());
        return page;
    }
}
