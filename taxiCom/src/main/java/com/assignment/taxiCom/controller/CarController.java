package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Car;
import com.assignment.taxiCom.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
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
    public Page<Car> getAllCars(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int pageSize) {
        return carService.getAllCars(page, pageSize);
    }

    @GetMapping(value = "/cars/make")
    public Page<Car> getCarByMake(@RequestParam(name = "value") String make,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int pageSize) {
        return carService.getCarByMake(make, page, pageSize);
    }

    @GetMapping(value = "/cars/model")
    public Page<Car> getCarByModel(@RequestParam(name = "value") String model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int pageSize) {
        return carService.getCarByModel(model, page, pageSize);
    }

    @GetMapping(value = "/cars/color")
    public Page<Car> getCarByColor(@RequestParam(name = "value") String color,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int pageSize) {
        return carService.getCarByColor(color, page, pageSize);
    }

    @GetMapping(value = "/cars/rating")
    public Page<Car> getCarByRating(@RequestParam(name = "value") int rating,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int pageSize) {
        return carService.getCarByRating(rating, page, pageSize);
    }

    @GetMapping(value = "/cars/rating/sort")
    public Page<Car> sortCarByRating(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int pageSize) {
        return carService.sortCarRating(page, pageSize);
    }

    @GetMapping(value = "/cars/rate")
    public Page<Car> getCarByRate(@RequestParam(name = "value") int rate,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int pageSize) {
        return carService.getCarByRate(rate, page, pageSize);
    }

    @GetMapping(value = "/cars/rate/sort")
    public Page<Car> sortCarByRate(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int pageSize) {
        return carService.sortCarRate(page, pageSize);
    }

    @GetMapping(value = "/cars/convertible")
    public Page<Car> getCarByConvertible(@RequestParam(name = "value") boolean conv,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int pageSize) {
        return carService.getCarByConvertible(conv, page, pageSize);
    }

    @GetMapping(value = "/cars/license")
    public Car getCarByLicense(@RequestParam(name = "value") String license) {
        return carService.getCarByLicense(license);
    }

    @GetMapping(value = "/cars/vin")
    public Car getCarByVin(@RequestParam(name = "value") String vin) {
        return carService.getCarByVin(vin);
    }

    @GetMapping("/cars/available")
    public Page<Car> getAvailable(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int pageSize) {

        return carService.getAvailable(page, pageSize);
    }

    @GetMapping(value = "/cars/usage")
    public Page<List> getUsage(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int pageSize){
        return carService.getUsage(page, pageSize);
    }


    @PostMapping(value = "/cars")
    public String addCar(@RequestBody Car car) {return carService.addCar(car);}

    @DeleteMapping("/cars")
    public String deleteCar(@RequestBody Car car) {return carService.deleteCar(car);}

    @PutMapping("/cars")
    public String updateCar(@RequestBody Car car) {return  carService.updateCar(car);}
}
