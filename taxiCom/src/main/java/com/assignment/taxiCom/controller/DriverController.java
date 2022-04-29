package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Driver;
import com.assignment.taxiCom.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping(value = "/drivers", params = "license")
    public List<Driver> getDriverByLicense(@RequestParam String license) {
        return driverService.getDriverByLicense(license);
    }

    @PostMapping("/drivers")
    public String addDriver(@RequestBody Driver driver){
        return driverService.addDriver(driver);
    }
}
