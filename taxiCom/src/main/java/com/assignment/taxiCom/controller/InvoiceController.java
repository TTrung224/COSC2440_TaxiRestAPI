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
        System.out.println(invoiceService.getAllInvoice(page, pageSize));
        return invoiceService.getAllInvoice(page, pageSize);
    }

    @PostMapping(value = "/invoices")
    public String addInvoice(@RequestBody Invoice invoice) {return invoiceService.addInvoice(invoice);}

//    @GetMapping(value ="/invoice")
//    public List<Invoice> filter(){
//
//
//    }

}
