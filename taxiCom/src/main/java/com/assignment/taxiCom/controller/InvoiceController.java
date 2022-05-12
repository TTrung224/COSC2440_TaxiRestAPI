package com.assignment.taxiCom.controller;

import com.assignment.taxiCom.entity.Invoice;
import com.assignment.taxiCom.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return invoiceService.getAllInvoice(page, pageSize);
    }

    @PostMapping(value = "/invoices")
    public Object addInvoice(
            @RequestParam(name = "bookingId") long bookingID,
            @RequestParam(name = "customerId") long customerID,
            @RequestParam(name = "carId") long carID,
            @RequestBody Invoice invoice) {
        return invoiceService.addInvoice(invoice, bookingID, customerID, carID);
    }

    @PutMapping(value="/invoices")
    public String updateInvoice(
            @RequestParam(name = "customerId") long customerID,
            @RequestParam(name = "driverId") long driverID,
            @RequestBody Invoice invoice
    ){
        return invoiceService.updateInvoice(invoice,customerID,driverID);
    }

    @DeleteMapping(value ="/invoices")
    public String deleteInvoice(
            @RequestParam long invoiceId
    ){
        return invoiceService.deleteInvoice(invoiceId);
    }

    @GetMapping(value ="/invoices/createdDate/{strStart}/{strEnd}")
    public Page<Invoice> filterInvoiceByPeriod(
            @PathVariable(name = "strStart") String strStart,
            @PathVariable(name = "strEnd") String strEnd,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        return invoiceService.filterInvoiceByPeriod(strStart,strEnd, page, pageSize);
    }

    @GetMapping(value ="/invoices/customerRevenue/{customerId}/{strStart}/{strEnd}")
    public double getCustomerRevenueByPeriod(
            @PathVariable(name = "customerId") long customerId,
            @PathVariable(name = "strStart") String strStart,
            @PathVariable(name = "strEnd") String strEnd
    ){
        return invoiceService.getCustomerRevenueByPeriod(customerId,strStart,strEnd);
    }

    @GetMapping(value ="/invoices/driverRevenue/{driverId}/{strStart}/{strEnd}")
    public double getDriverRevenueByPeriod(
            @PathVariable(name = "driverId") long driverId,
            @PathVariable(name = "strStart") String strStart,
            @PathVariable(name = "strEnd") String strEnd
    ){
        return invoiceService.getDriverRevenueByPeriod(driverId,strStart,strEnd);
    }

    @GetMapping(value ="/invoices/customerInvoice/{customerId}/{strStart}/{strEnd}")
    public Page<Invoice> getCustomerInvoiceByPeriod(
            @PathVariable(name = "customerId") long customerId,
            @PathVariable(name = "strStart") String strStart,
            @PathVariable(name = "strEnd") String strEnd,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        return invoiceService.getCustomerInvoiceByPeriod(customerId,strStart,strEnd,page, pageSize);
    }

    @GetMapping(value ="/invoices/driverInvoice/{driverId}/{strStart}/{strEnd}")
    public Page<Invoice> getDriverInvoiceByPeriod(
            @PathVariable(name = "driverId") long driverId,
            @PathVariable(name = "strStart") String strStart,
            @PathVariable(name = "strEnd") String strEnd,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        return invoiceService.getDriverInvoiceByPeriod(driverId,strStart,strEnd,page, pageSize);
    }
}
