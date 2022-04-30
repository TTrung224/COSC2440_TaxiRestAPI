package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Booking;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<Booking> resultList = criteria.list();
        Page<Booking> page = new PageImpl<>(resultList);
        return page;
    }

    public Booking getBookingById(Long id){
        return sessionFactory.getCurrentSession().get(Booking.class, id);
    }

}
