package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Customer;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory(){
        return sessionFactory;
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

    public int saveCustomer(Customer customer){
        sessionFactory.getCurrentSession().save(customer);
        return customer.getId();
    }

    public Customer getCustomerByID(Long ID){
        return sessionFactory.getCurrentSession().get(Customer.class,ID);
    }
    public List<Customer> getCustomerByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        return criteria.list();
    }

    public List<Customer> getCustomerByAddress(String address) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        criteria.add(Restrictions.like("address", address, MatchMode.ANYWHERE));
        return criteria.list();
    }


    public Customer getCustomerByPhone(int phone){
        return (Customer)sessionFactory.getCurrentSession().get(Customer.class, phone);
    }

}
