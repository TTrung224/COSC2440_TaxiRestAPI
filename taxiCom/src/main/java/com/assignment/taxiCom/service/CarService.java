package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Car;
import com.assignment.taxiCom.repository.CarRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
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

    public String addCar(Car car){
        sessionFactory.getCurrentSession().save(car);
        return String.format("Car with ID %1$s is added (%2$s)", car.getId(), car.getDateCreated());
    }

    public String deleteCar(Car car){
        sessionFactory.getCurrentSession().delete(car);
        return String.format("Car with ID %s is deleted", car.getId());
    }

    public String updateCar(Car car) {
        sessionFactory.getCurrentSession().update(car);
        return String.format("Car with ID %s has been updated", car.getId());
    }


    public Page<Car> getAllCars(int page, int pageSize) {
        return carRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Page<Car> getCarById(long id) {
        return carRepository.findCarById(id, PageRequest.of(0, 1));
    }

    public Page<Car> getCarByVin(String vin) {
        return carRepository.findCarByVin(vin, PageRequest.of(0, 1));
    }

    public Page<Car> getCarByLicense(String license) {
        return carRepository.findCarByLicensePlate(license, PageRequest.of(0, 1));
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
}
