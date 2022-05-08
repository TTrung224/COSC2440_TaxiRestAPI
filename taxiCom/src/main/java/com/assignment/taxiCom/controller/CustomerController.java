package com.assignment.taxiCom.controller;

import java.util.List;

import com.assignment.taxiCom.entity.Customer;
import com.assignment.taxiCom.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    public CustomerService getCustomerService(){
        return customerService;
    }

    public void setCustomerService(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping(path = "/customer")
    public long addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

//    @PutMapping(path = "/customer")
//    public Customer updateCustomer(@RequestBody Customer customer){
//        return customerService.updateCustomer(customer);
//    }
//
//    @DeleteMapping(path = "/customer")
//    public long deleteCustomer(@RequestBody Customer customer){
//        return customerService.deleteCustomer(customer);
//    }

//    @GetMapping(path="/customers/searchByPhone")
//    public Customer getCustomerByPhone (@RequestParam int phone){
//        return customerService.getCustomerByPhone(phone);
//    }
//
//    @GetMapping(path = "/customers/searchByID")
//    public Customer getCustomerByID(@RequestParam long ID){
//        return customerService.getCustomerByID(ID);
//    }
//
//    @GetMapping(path = "/customer/filterCustomerByCreatedTime/{strStart}/{strEnd}")
//    public Page<Customer> filterCustomerCreatedTime(
//            @PathVariable String strStart,
//            @PathVariable String strEnd,
//            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
//            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
//    ){
//        return customerService.filterCustomerByCreatedTime(strStart, strEnd, page, pageSize);
//
//    }
//
//    @GetMapping(path = "/customer/filterCustomerByName/{name}")
//    public Page<Customer> filterCustomerByName(
//            @PathVariable String name,
//            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
//            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
//    ){
//        return customerService.filterCustomerByName(name, page, pageSize);
//    }





}

