package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Driver;
import com.assignment.taxiCom.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @PostMapping(value = "/drivers")
    public String addDriver(@RequestBody Driver driver){
        return driverService.addDriver(driver);
    }
}
