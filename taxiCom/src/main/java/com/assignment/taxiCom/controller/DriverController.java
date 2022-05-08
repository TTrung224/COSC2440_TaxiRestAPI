package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Customer;
import com.assignment.taxiCom.entity.Driver;
import com.assignment.taxiCom.service.CustomerService;
import com.assignment.taxiCom.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;


    public DriverService driverService() {
        return driverService;
    }

    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }



    @PostMapping(value = "/drivers")
    public long addDriver(@RequestBody Driver driver) {return driverService.addDriver(driver);}
}
