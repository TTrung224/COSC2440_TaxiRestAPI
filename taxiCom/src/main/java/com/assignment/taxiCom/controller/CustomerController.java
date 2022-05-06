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

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer){
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping
    public long deleteCustomer(@RequestBody Customer customer){
        return customerService.deleteCustomer(customer);
    }

    @GetMapping(path = "/customers/searchByName")
    public List<Customer> getCustomerByName(@RequestParam String name){
        return customerService.getCustomerByName(name);
    }

    @GetMapping(path = "/customers/searchByAddress")
    public List<Customer> getCustomerByAddress(@RequestParam String address){
        return customerService.getCustomerByAddress(address);
    }

    @GetMapping(path="/customers/searchByPhone")
    public Customer getCustomerByPhone (@RequestParam int phone){
        return customerService.getCustomerByPhone(phone);
    }

    @GetMapping(path = "/customers/searchByID")
    public Customer getCustomerByID(@RequestParam long ID){
        return customerService.getCustomerByID(ID);
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

