package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Invoice;
import com.assignment.taxiCom.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;


    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping(value = "/invoices")
    public Page<Invoice> getAllInvoice(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize){

        return invoiceService.getAllInvoice(page, pageSize);
    }

    @PostMapping(value = "/invoices")
    public long addInvoice(
            @RequestParam(name = "customerID") long customerID,
            @RequestParam(name = "driverID") long driverID,
            @RequestBody Invoice invoice) {
        return invoiceService.addInvoice(invoice, customerID, driverID);
    }

    @GetMapping(value ="/invoices/filterByCreatedDate/{strStart}/{strEnd}")
    public Page<Invoice> filterInvoiceByPeriod(
            @PathVariable(name = "strStart") String strStart,
            @PathVariable(name = "strEnd") String strEnd,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return invoiceService.filterInvoiceByPeriod(strStart,strEnd, page, pageSize);
    }

    @GetMapping(value ="/invoices/getCustomerRevenue/{customerId}/{strStart}/{strEnd}")
    public double getCustomerRevenueByPeriod(
            @PathVariable(name = "customerId") long customerId,
            @PathVariable(name = "strStart") String strStart,
            @PathVariable(name = "strEnd") String strEnd
    ){
        return invoiceService.getCustomerRevenueByPeriod(customerId,strStart,strEnd);
    }

    @GetMapping(value ="/invoices/getDriverRevenue/{driverId}/{strStart}/{strEnd}")
    public double getDriverRevenueByPeriod(
            @PathVariable(name = "driverId") long driverId,
            @PathVariable(name = "strStart") String strStart,
            @PathVariable(name = "strEnd") String strEnd
    ){
        return invoiceService.getDriverRevenueByPeriod(driverId,strStart,strEnd);
    }

    @GetMapping(value ="/invoices/getCustomerInvoice/{customerId}/{strStart}/{strEnd}")
    public Page<Invoice> getCustomerInvoiceByPeriod(
            @PathVariable(name = "customerId") long customerId,
            @PathVariable(name = "strStart") String strStart,
            @PathVariable(name = "strEnd") String strEnd,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return invoiceService.getCustomerInvoiceByPeriod(customerId,strStart,strEnd,page, pageSize);
    }

    @GetMapping(value ="/invoices/getCustomerInvoice/{driverId}/{strStart}/{strEnd}")
    public Page<Invoice> getDriverInvoiceByPeriod(
            @PathVariable(name = "driverId") long driverId,
            @PathVariable(name = "strStart") String strStart,
            @PathVariable(name = "strEnd") String strEnd,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return invoiceService.getDriverInvoiceByPeriod(driverId,strStart,strEnd,page, pageSize);
    }
}
