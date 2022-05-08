package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Booking;
import com.assignment.taxiCom.entity.Customer;
import com.assignment.taxiCom.entity.Invoice;
import com.assignment.taxiCom.repository.InvoiceRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class InvoiceService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }

    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long addInvoice(Invoice invoice, long customerID, long driverID){
        sessionFactory.getCurrentSession().save(invoice);
        invoice.setCustomer(customerService.getCustomerByID(customerID));
        invoice.setDriver(driverService.getDriverByID(driverID));
        return invoice.getId();
    }

    public Page<Invoice> getAllInvoice(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Invoice> invoices = invoiceRepository.findAll(pageable);
        return invoices;
    }

    public Invoice getInvoiceByID(long id){
        return sessionFactory.getCurrentSession().get(Invoice.class, id);
    }

    public Page<Invoice> filterInvoiceByPeriod(String strStart,String strEnd, int page, int pageSize){
        ZonedDateTime startDay = ZonedDateTime.parse(strStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        ZonedDateTime enDay = ZonedDateTime.parse(strEnd,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Invoice> invoices = invoiceRepository.filterInvoiceByPeriod(startDay,enDay,pageable);
        return invoices;
    }

    public double getCustomerRevenueByPeriod(long customerId,String strStart,String strEnd){
        ZonedDateTime startDay = ZonedDateTime.parse(strStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        ZonedDateTime enDay = ZonedDateTime.parse(strEnd,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        return invoiceRepository.getCustomerRevenueByPeriod(customerId,startDay,enDay);
    }

    public double getDriverRevenueByPeriod(long driverId,String strStart,String strEnd){
        ZonedDateTime startDay = ZonedDateTime.parse(strStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        ZonedDateTime enDay = ZonedDateTime.parse(strEnd,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        return invoiceRepository.getDriverRevenueByPeriod(driverId,startDay,enDay);
    }

    public Page<Invoice> getCustomerInvoiceByPeriod(long customerId,String strStart,String strEnd, int page, int pageSize){
        ZonedDateTime startDay = ZonedDateTime.parse(strStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        ZonedDateTime enDay = ZonedDateTime.parse(strEnd,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Invoice> invoices = invoiceRepository.getCustomerByPeriod(customerId,startDay,enDay,pageable);
        return invoices;
    }

    public Page<Invoice> getDriverInvoiceByPeriod(long driverId,String strStart,String strEnd, int page, int pageSize){
        ZonedDateTime startDay = ZonedDateTime.parse(strStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        ZonedDateTime enDay = ZonedDateTime.parse(strEnd,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Invoice> invoices = invoiceRepository.getDriverByPeriod(driverId,startDay,enDay,pageable);
        return invoices;
    }
}
