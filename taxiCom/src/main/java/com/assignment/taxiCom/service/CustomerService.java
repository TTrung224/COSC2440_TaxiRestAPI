package com.assignment.taxiCom.service;

import com.assignment.taxiCom.entity.Customer;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Transactional
public class CustomerService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int saveCustomer(Customer customer){
        sessionFactory.getCurrentSession().save(customer);
        return customer.getId();
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
