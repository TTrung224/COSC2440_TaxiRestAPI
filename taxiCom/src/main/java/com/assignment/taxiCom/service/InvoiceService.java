package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Booking;
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
import java.util.List;

@Service
@Transactional
public class InvoiceService {
    @Autowired
    private SessionFactory sessionFactory;

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


    public String addInvoice(Invoice invoice){
        sessionFactory.getCurrentSession().save(invoice);
        return String.format("Invoice with ID %1$s is added (%2$s)", invoice.getId());

    }

    public Page<Invoice> getAllInvoice(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Invoice> invoices = invoiceRepository.findAll(pageable);
        return invoices;
    }

    public Page<Invoice> filterInvoiceByPeriod(String strStart,String strEnd, int page, int pageSize){
        ZonedDateTime startDay = ZonedDateTime.parse(strStart);
        ZonedDateTime enDay = ZonedDateTime.parse(strEnd);
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Invoice> invoices = invoiceRepository.filterInvoiceByPeriod(startDay,enDay,pageable);
        return invoices;
    }

    public Page<Invoice> filterInvoiceByDate(String strDate, int page, int pageSize){
        ZonedDateTime date = ZonedDateTime.parse(strDate);
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Invoice> invoices = invoiceRepository.filterInvoiceByDate(date,pageable);
        return invoices;
    }
}
