package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Booking;
import com.assignment.taxiCom.entity.Customer;
import com.assignment.taxiCom.entity.Driver;
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
    private BookingService bookingService;

    @Autowired
    private CarService carService;

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

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public DriverService getDriverService() {
        return driverService;
    }

    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    public BookingService getBookingService() {
        return bookingService;
    }

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public CarService getCarService() {
        return carService;
    }

    public void setCarService(CarService carService) {
        this.carService = carService;
    }

    public long addInvoice(Invoice invoice, long bookingID, long customerID, long carID){
        Booking booking = bookingService.getBookingById(bookingID);
        booking.setInvoice(invoice);

        invoice.setCustomer(customerService.getCustomerByID(customerID));
        customerService.getCustomerByID(customerID).getInvoice().add(invoice);

        Driver driver = carService.getCarById(carID).getDriver();
        invoice.setDriver(driver);
        driver.getInvoice().add(invoice);

        invoice.setTotalCharge(booking.getDistance() * invoice.getDriver().getCar().getRatePerKilometer());

        sessionFactory.getCurrentSession().save(invoice);
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
        Page<Invoice> invoices = invoiceRepository.getCustomerInvoiceByPeriod(customerId,startDay,enDay,pageable);
        return invoices;
    }

    public Page<Invoice> getDriverInvoiceByPeriod(long driverId,String strStart,String strEnd, int page, int pageSize){
        ZonedDateTime startDay = ZonedDateTime.parse(strStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        ZonedDateTime enDay = ZonedDateTime.parse(strEnd,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Invoice> invoices = invoiceRepository.getDriverInvoiceByPeriod(driverId,startDay,enDay,pageable);
        return invoices;
    }

    public Invoice updateInvoice(Invoice invoice, long customerID, long driverID){
        sessionFactory.getCurrentSession().update(invoice);
        invoice.setCustomer(customerService.getCustomerByID(customerID));
        invoice.setDriver(driverService.getDriverById(driverID));
        return invoice;
    }

    public long deleteInvoice(Invoice invoice){
        sessionFactory.getCurrentSession().delete(invoice);
        return invoice.getId();
    }
}
