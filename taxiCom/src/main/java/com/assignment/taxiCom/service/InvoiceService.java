package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Invoice;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class InvoiceService {
    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public Long addInvoice(Invoice invoice){
        sessionFactory.getCurrentSession().save(invoice);
//        return String.format("Invoice with ID %1$s is added (%2$s)", invoice.getId());
        return invoice.getId();
    }

    public List<Invoice> getAllInvoices() {
        return sessionFactory.getCurrentSession().createCriteria(Invoice.class).list();
    }

//    public List<Invoice> getAllInvoiceByPeriod(){
//
//    }
}
