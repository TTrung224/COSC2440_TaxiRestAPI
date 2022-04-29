package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Car;
import com.assignment.taxiCom.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    public CarService getCarService() {
        return carService;
    }

    public void setCarService(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(value = "/cars")
    public List<Car> getAllCars() {return carService.getAllCars();}

    @PostMapping(value = "/cars")
    public String addCar(@RequestBody Car car) {return carService.addCar(car);}
}
