package com.assignment.taxiCom.controller;

import java.util.List;

import com.assignment.taxiCom.entity.Customer;
import com.assignment.taxiCom.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(path = {"/customers"}, method = RequestMethod.GET)

    public List<Customer> getCustomerByName(String name){
        return customerService.getCustomerByName(name);
    }

    @RequestMapping(path = {"/customers"}, method = RequestMethod.GET)

    public List<Customer> getCustomerByAddress(String address){
        return customerService.getCustomerByName(address);
    }

    @RequestMapping(value="/method9")
    public String method9(@RequestParam("phone") int phone){
        return "method9 with phone= "+phone;
    }

    @RequestMapping(path  =  {"/customers"},  method  =
            RequestMethod.POST)
    public  void  saveCustomer(@RequestBody  Customer
                                      customer){
        customerService.saveCustomer(customer);
    }

    @Controller
    @RequestMapping("/home")
    public class HomeController {
    }

    @RequestMapping()
    public String defaultMethod(){
        return "default method";
    }

    @RequestMapping("*")
    public String fallbackMethod(){
        return "fallback method";
    }




}

