package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Car;
import com.assignment.taxiCom.entity.Driver;
import com.assignment.taxiCom.repository.CarRepository;
import com.assignment.taxiCom.repository.DriverRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.time.ZoneId;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@Transactional
public class CarService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CarRepository carRepository;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CarRepository getCarRepository() {
        return carRepository;
    }

    public void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public ResponseEntity<?> addCar(Car car){
        sessionFactory.getCurrentSession().save(car);
        return new ResponseEntity<>(String.format("Car with ID %1$s is added (%2$s)", car.getId(), car.getDateCreated()), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteCar(long carId){
        Car car = getCarById(carId);
        if(car == null){
            return new ResponseEntity<>("Car does not exist", HttpStatus.BAD_REQUEST);
        }
        if(car.getDriver() != null){
            Driver driver = car.getDriver();
            driver.setCar(null);
        }
        sessionFactory.getCurrentSession().delete(car);
        return new ResponseEntity<>("Car has been deleted", HttpStatus.OK);
    }

    public ResponseEntity<?> updateCar(Car car) {
        sessionFactory.getCurrentSession().update(car);
        return new ResponseEntity<>(String.format("Car with ID %s has been updated", car.getId()), HttpStatus.OK);
    }

    public Page<Car> getAllCars(int page, int pageSize) {
        return carRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Car getCarById(long id) {
        return carRepository.findCarById(id);
    }

    public Car getCarByVin(String vin) {
        return carRepository.findCarByVin(vin);
    }

    public Car getCarByLicense(String license) {
        return carRepository.findCarByLicensePlate(license);
    }

    public Page<Car> getCarByMake(String make, int page, int pageSize){
        return carRepository.findCarByMake(make, PageRequest.of(page, pageSize));
    }

    public Page<Car> getCarByModel(String model, int page, int pageSize){
        return carRepository.findCarByModel(model, PageRequest.of(page, pageSize));
    }

    public Page<Car> getCarByRating(int rating, int page, int pageSize){
        return carRepository.findCarByRating(rating, PageRequest.of(page, pageSize));
    }

    public Page<Car> getCarByRate(int ratePerKilometer, int page, int pageSize){
        return carRepository.findCarByRate(ratePerKilometer, PageRequest.of(page, pageSize));
    }

    public Page<Car> getCarByConvertible(boolean convertible, int page, int pageSize){
        return carRepository.findCarByConvertible(convertible, PageRequest.of(page, pageSize));
    }

    public Page<Car> getCarByColor(String color, int page, int pageSize){
        return carRepository.findCarByColor(color, PageRequest.of(page, pageSize));
    }

    public Page<Car> sortCarRating(int page, int pageSize){
        return carRepository.findAll(PageRequest.of(page, pageSize, Sort.by("rating").ascending()));
    }

    public Page<Car> sortCarRate(int page, int pageSize){
        return carRepository.findAll(PageRequest.of(page, pageSize, Sort.by("ratePerKilometer").ascending()));
    }

    public Page<Car> getAvailable(int page, int pageSize) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT D.car from Driver D");
        System.out.println(query.getResultList().size());
        if(query.getResultList().size() > 0){
            return  carRepository.getAvailable(PageRequest.of(page, pageSize));
        }
        else {
            return carRepository.findAll(PageRequest.of(page, pageSize));
        }
    }

    public Page<Map<String, Integer>> getUsage(int month, int year, int page, int pageSize) {
        return carRepository.getUsage(month, year, PageRequest.of(page, pageSize)); 
    }

    public ResponseEntity<?> getAvailableForBooking(String strPickUp, int page, int pageSize){
        ZonedDateTime pickUp = ZonedDateTime.parse(strPickUp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        ZoneId zone = pickUp.getZone();
        ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(zone);

        if(!pickUp.truncatedTo(ChronoUnit.DAYS).isEqual(now.truncatedTo(ChronoUnit.DAYS))) {
            return new ResponseEntity<>("Can not have pick-up time for other date", HttpStatus.FORBIDDEN);
        } else if(pickUp.isBefore(now)) {
            return new ResponseEntity<>("Can not book car for time in the past", HttpStatus.FORBIDDEN);
        } else {
            Pageable pageable = PageRequest.of(page, pageSize);
            return new ResponseEntity<>(carRepository.getAvailableForBooking(pickUp, pageable), HttpStatus.OK);
        }
    }
}
