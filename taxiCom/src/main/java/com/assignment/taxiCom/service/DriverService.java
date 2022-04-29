package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Driver;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

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

    public String addDriver(Driver driver){
        sessionFactory.getCurrentSession().save(driver);
        return String.format("Driver with ID %1$s is added (%2$s)", driver.getId(), driver.getDateCreated());
    }

    public List<Driver> getAllDrivers(){
        return sessionFactory.getCurrentSession().createCriteria(Driver.class).list();
    }

    public List<Driver> getDriverByLicense(String license) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Driver D where D.licenseNumber = :license");
        query.setParameter("license", license);
        return query.getResultList();
    }
}
