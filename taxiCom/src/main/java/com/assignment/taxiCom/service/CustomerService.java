package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Customer;
import com.assignment.taxiCom.repository.CustomerRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@Service
@Transactional
public class CustomerService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CustomerRepository customerRepository;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<Customer> getAllCustomers(int page, int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize, Sort.by("dateCreated").ascending());
        return (Page<Customer>) customerRepository.findAll(pageable);
    }

    public long addCustomer(Customer customer){
        sessionFactory.getCurrentSession().saveOrUpdate(customer);
        return customer.getId();
    }

    public Customer updateCustomer(Customer customer){
        sessionFactory.getCurrentSession().update(customer);
        return customer;
    }

    public long deleteCustomer(Customer customer){
        sessionFactory.getCurrentSession().delete(customer);
        return customer.getId();
    }

    public Customer getCustomerByID(long ID){
        return sessionFactory.getCurrentSession().get(Customer.class,ID);
    }

    public Customer getCustomerByPhone(String phone){
        return customerRepository.findCustomerByPhone(phone);
    }

    public Page<Customer> filterCustomerByCreatedTime(String strStart, String strEnd, int page, int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize, Sort.by("dateCreated").ascending());
        ZonedDateTime start = ZonedDateTime.parse(strStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        ZonedDateTime end = ZonedDateTime.parse(strEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        Page<Customer> customers = customerRepository.filterCustomerByCreatedTime(start, end, pageable);
        return customers;
    }

    public Page<Customer> filterCustomerByName(String name, int page, int pageSize){
        name = name.toUpperCase();
        Pageable pageable = PageRequest.of(page,pageSize, Sort.by("name").ascending());
        Page<Customer> customers = customerRepository.filterCustomerByName(name, pageable);
        return customers;
    }

    public Page<Customer> filterCustomerByAddress(String address, int page, int pageSize){
        address = address.toUpperCase();
        Pageable pageable = PageRequest.of(page,pageSize, Sort.by("address").ascending());
        Page<Customer> customers = customerRepository.filterCustomerByAddress(address, pageable);
        return customers;
    }

}
