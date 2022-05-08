package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Customer;
import com.assignment.taxiCom.entity.Invoice;
import com.assignment.taxiCom.service.CustomerService;
import com.assignment.taxiCom.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }



    @PostMapping(value = "/customers")
    public long addCustomer(@RequestBody Customer customer) {return customerService.addCustomer(customer);}
}
