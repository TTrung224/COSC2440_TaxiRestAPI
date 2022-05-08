package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Customer;
import com.assignment.taxiCom.entity.Driver;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DriverService {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long addDriver(Driver driver){
        sessionFactory.getCurrentSession().save(driver);
        return driver.getId();
    }

    public Driver getDriverByID(long id){
        return sessionFactory.getCurrentSession().get(Driver.class, id);
    }
}
