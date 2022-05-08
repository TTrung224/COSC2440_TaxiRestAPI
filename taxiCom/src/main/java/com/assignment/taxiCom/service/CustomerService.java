package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Customer;
import com.assignment.taxiCom.entity.Invoice;
import com.assignment.taxiCom.repository.InvoiceRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long addCustomer(Customer customer){
        sessionFactory.getCurrentSession().save(customer);
        return customer.getId();
    }

    public Customer getCustomerByID(long id){
        return sessionFactory.getCurrentSession().get(Customer.class, id);
    }
}
