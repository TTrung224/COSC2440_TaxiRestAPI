package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Driver;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public String deleteDriver(Driver driver){
        sessionFactory.getCurrentSession().delete(driver);
        return String.format("Driver with ID %s is deleted", driver.getId());
    }

    public String updateDriver(Driver driver){
        sessionFactory.getCurrentSession().update(driver);
        return String.format("Driver with ID %s has been updated", driver.getId());
    }

    public List<Driver> getAllDrivers(int page, int pageSize){
        Query query = sessionFactory.getCurrentSession().createQuery("from Driver ");
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<Driver> getDriverByLicense(String license) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Driver D where D.licenseNumber = :license");
        query.setParameter("license", license);
        return query.getResultList();
    }

    public List<Driver> getDriverByRating(int rating, int page, int pageSize) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Driver D where D.rating = :rating");
        query.setParameter("rating", rating);
        query.setFirstResult((page - 1) * pageSize);
        return query.getResultList();
    }
}
