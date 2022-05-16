package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Customer;
import com.assignment.taxiCom.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "/customers")
    public Page<Customer> getAllCustomers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return customerService.getAllCustomers(page, pageSize);
    }

    @PostMapping(path = "/customers")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    @PutMapping(path = "/customers")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer){
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping(path = "/customers")
    public ResponseEntity<?> deleteCustomer(@RequestParam long customerId){
        return customerService.deleteCustomer(customerId);
    }

    @GetMapping(path="/customers/phone")
    public Customer getCustomerByPhone (@RequestParam(name = "phone") String phone){
        return customerService.getCustomerByPhone(phone);
    }

    @GetMapping(path = "/customers/id")
    public Customer getCustomerByID(@RequestParam(name = "customerId") long ID){
        return customerService.getCustomerByID(ID);
    }

    @GetMapping(path = "/customers/createdTime/{strStart}/{strEnd}")
    public Page<Customer> filterCustomerCreatedTime(
            @PathVariable String strStart,
            @PathVariable String strEnd,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        return customerService.filterCustomerByCreatedTime(strStart, strEnd, page, pageSize);
    }

    @GetMapping(path = "/customers/name")
    public Page<Customer> filterCustomerByName(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        return customerService.filterCustomerByName(name, page, pageSize);
    }

    @GetMapping(path = "/customers/address")
    public Page<Customer> filterCustomerByAddress(
            @RequestParam(name = "address") String address,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        return customerService.filterCustomerByAddress(address, page, pageSize);
    }
}

