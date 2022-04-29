package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Car;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarService {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String addCar(Car car){
        sessionFactory.getCurrentSession().save(car);
        return String.format("Car with ID %1$s is added (%2$s)", car.getVin(), car.getDateCreated());
    }

    public List<Car> getAllCars() {
        return sessionFactory.getCurrentSession().createCriteria(Car.class).list();
    }
}
