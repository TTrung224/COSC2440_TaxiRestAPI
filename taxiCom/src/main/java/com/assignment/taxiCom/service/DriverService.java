package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Car;
import com.assignment.taxiCom.entity.Driver;
import com.assignment.taxiCom.entity.Invoice;
import com.assignment.taxiCom.repository.CarRepository;
import com.assignment.taxiCom.repository.DriverRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@Transactional
public class DriverService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

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

    public String deleteDriver(long driverId){
        Driver driver = getDriverById(driverId);
        if (driver == null){
            return "Driver not found";
        }
        for(Invoice invoice : driver.getInvoice()){
            invoice.getBooking().setInvoice(null);
            sessionFactory.getCurrentSession().delete(invoice);
        }
        sessionFactory.getCurrentSession().delete(driver);
        return String.format("Driver with ID %s is deleted", driver.getId());
    }

    public String updateDriver(Driver driver){
        if (driver.getCar() != null){
            driver.getCar().setDriver(driver);

        }
        sessionFactory.getCurrentSession().update(driver);
        return String.format("Driver with ID %s has been updated", driver.getId());
    }

    public ResponseEntity<?> assignCar(long driverId, long carId){
        Car car = carRepository.findCarById(carId);
        Driver driver = getDriverById(driverId);
        if(driver == null){return new ResponseEntity<>("Driver does not exist", HttpStatus.BAD_REQUEST);}
        if(car != null){
            if(car.getDriver() != null){
                return new ResponseEntity<>("Car already assigned to another driver", HttpStatus.FORBIDDEN);
            }
            else{
                driver.setCar(car);
                sessionFactory.getCurrentSession().update(driver);
                car.setDriver(driver);
                sessionFactory.getCurrentSession().update(car);
                return new ResponseEntity<>(String.format("Car with ID %1$s has been assigned to Driver with ID %2$s", car.getId(), driver.getId()), HttpStatus.OK);
            }
        }
        else{
            return new ResponseEntity<>("Car does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> unassignCar(long driverId) {
        Driver driver = getDriverById(driverId);
        if(driver == null){return new ResponseEntity<>("Driver does not exist", HttpStatus.BAD_REQUEST);}
        Car car = driver.getCar();
        if(car == null){
            return new ResponseEntity<>("Driver does not have a car", HttpStatus.BAD_REQUEST);
        }else {
            driver.setCar(null);
            sessionFactory.getCurrentSession().update(driver);
            car.setDriver(null);
            sessionFactory.getCurrentSession().update(car);
            return new ResponseEntity<>("Driver no longer assigned to any car", HttpStatus.OK);
        }
    }

    public Page<Driver> getAllDrivers(int page, int pageSize){
        return driverRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Driver getDriverById(long id) {
        return driverRepository.findDriverById(id);
    }

    public Driver getDriverByLicense(String license) {
        return driverRepository.findDriverByLicense(license);
    }

    public Page<Driver> getDriverByRating(int rating, int page, int pageSize) {
        return driverRepository.findDriverByRating(rating, PageRequest.of(page, pageSize));
    }

    public Driver getDriverByPhone(String phoneNum) {
        return driverRepository.findDriverByPhone(phoneNum);
    }

    public Page<Driver> sortDriverRating(int page, int pageSize) {
        return driverRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Driver getDriverByCar(long carId){
        return driverRepository.findDriverByCar(carId);
    }
}
