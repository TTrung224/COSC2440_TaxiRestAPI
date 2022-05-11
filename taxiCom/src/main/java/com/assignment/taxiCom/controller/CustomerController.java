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

    @Autowired
    private InvoiceService invoiceService;

    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public CustomerService getCustomerService(){
        return customerService;
    }

    public void setCustomerService(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping(path = "/customers")
    public Page<Customer> getAllCustomers(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize){
        return customerService.getAllCustomers(page, pageSize);
    }

    @PostMapping(path = "/customers")
    public long addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    @PutMapping(path = "/customers")
    public Customer updateCustomer(@RequestBody Customer customer){
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping(path = "/customers")
    public long deleteCustomer(@RequestBody Customer customer){
        Customer currentCustomer = customerService.getCustomerByID(customer.getId());
        for(Invoice invoice : currentCustomer.getInvoice()){
            invoiceService.deleteInvoice(invoice);
        }
        return customerService.deleteCustomer(customer);
    }

    @GetMapping(path="/customers/phone/{phone}")
    public Customer getCustomerByPhone (@PathVariable(name = "phone") String phone){
        return customerService.getCustomerByPhone(phone);
    }

    @GetMapping(path = "/customers/id/{ID}")
    public Customer getCustomerByID(@PathVariable long ID){
        return customerService.getCustomerByID(ID);
    }

    @GetMapping(path = "/customers/createdTime/{strStart}/{strEnd}")
    public Page<Customer> filterCustomerCreatedTime(
            @PathVariable String strStart,
            @PathVariable String strEnd,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return customerService.filterCustomerByCreatedTime(strStart, strEnd, page, pageSize);

    }

    @GetMapping(path = "/customers/name/{name}")
    public Page<Customer> filterCustomerByName(
            @PathVariable String name,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return customerService.filterCustomerByName(name, page, pageSize);
    }

    @GetMapping(path = "/customers/address/{address}")
    public Page<Customer> filterCustomerByAddress(
            @PathVariable(name = "address") String address,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return customerService.filterCustomerByAddress(address, page, pageSize);
    }


}

