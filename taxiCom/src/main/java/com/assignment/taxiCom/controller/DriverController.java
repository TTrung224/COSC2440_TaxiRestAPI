package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Driver;
import com.assignment.taxiCom.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    public DriverService getDriverService() {
        return driverService;
    }

    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping(value = "/drivers")
    public Page<Driver> getAllDrivers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int pageSize) {
        return driverService.getAllDrivers(page, pageSize);
    }

    @GetMapping(value = "/drivers/license")
    public Page<Driver> getDriverByLicense(@RequestParam(name = "value") String license) {
        return driverService.getDriverByLicense(license);
    }

    @GetMapping(value = "/drivers/rating")
    public Page<Driver> getDriverByRating(@RequestParam(name = "value") int rating,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int pageSize){
        return driverService.getDriverByRating(rating, page, pageSize);
    }

    @PostMapping("/drivers")
    public String addDriver(@RequestBody Driver driver){
        return driverService.addDriver(driver);
    }

    @DeleteMapping("/drivers")
    public String deleteDriver(@RequestBody Driver driver) {return  driverService.deleteDriver(driver);}

    @PutMapping ("/drivers")
    public String updateDriver(@RequestBody Driver driver) {return driverService.updateDriver(driver);}

    @PutMapping("/drivers/assign")
    public String assignCar(@RequestBody Driver driver, @RequestParam(name = "car_id") long id){
        return driverService.assignCar(driver, id);
    }
}
