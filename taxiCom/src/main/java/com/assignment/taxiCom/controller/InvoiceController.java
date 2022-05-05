package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Invoice;
import com.assignment.taxiCom.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Invoice> getAllCars() {return invoiceService.getAllInvoices();}

    @PostMapping(value = "/invoices")
    public String addCar(@RequestBody Invoice invoice) {return invoiceService.addInvoice(invoice);}

}
