package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Car;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
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


    public List<Car> getAllCars(int page, int pageSize) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Car ");
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}
