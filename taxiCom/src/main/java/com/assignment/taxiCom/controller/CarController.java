package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Car;
import com.assignment.taxiCom.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Car> getAllCars(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int pageSize) {
        return carService.getAllCars(page, pageSize);
    }

    @PostMapping(value = "/cars")
    public String addCar(@RequestBody Car car) {return carService.addCar(car);}

    @DeleteMapping("/cars")
    public String deleteCar(@RequestBody Car car) {return carService.deleteCar(car);}

    @PatchMapping("/cars")
    public String updateCar(@RequestBody Car car) {return  carService.updateCar(car);}
}
