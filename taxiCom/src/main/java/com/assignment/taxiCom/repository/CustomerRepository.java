package com.assignment.taxiCom.repository;

import com.assignment.taxiCom.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {

    Page<Customer> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM customer c WHERE c.dateCreated >= ?1 AND c.dateCreated <= ?2", nativeQuery = true)
    Page<Customer> filterCustomerByCreatedTime(ZonedDateTime startTime, ZonedDateTime endTime, Pageable pageable);

    @Query(value = "SELECT * FROM customer c WHERE UPPER(c.name) LIKE %?1%", nativeQuery = true)
    Page<Customer> filterCustomerByName(String name, Pageable pageable);

    @Query(value = "SELECT * FROM customer c WHERE UPPER(c.address) LIKE %?1%", nativeQuery = true)
    Page<Customer> filterCustomerByAddress(String address, Pageable pageable);

    @Query(value = "SELECT * FROM customer c WHERE c.phone LIKE ?1", nativeQuery = true)
    Customer findCustomerByPhone(String phone);

}
