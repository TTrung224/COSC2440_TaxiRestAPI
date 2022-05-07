package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Driver;
import com.assignment.taxiCom.repository.DriverRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Service
@Transactional
public class DriverService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DriverRepository driverRepository;

    public DriverRepository getDriverRepository() {
        return driverRepository;
    }

    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

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

    public Page<Driver> getAllDrivers(int page, int pageSize){
        return driverRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Page<Driver> getDriverByLicense(String license) {
        return driverRepository.findDriverByLicense(license, PageRequest.of(0, 1));
    }

    public Page<Driver> getDriverByRating(int rating, int page, int pageSize) {
        return driverRepository.findDriverByRating(rating, PageRequest.of(page, pageSize));
    }
}
