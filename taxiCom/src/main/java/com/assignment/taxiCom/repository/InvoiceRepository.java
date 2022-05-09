package com.assignment.taxiCom.repository;

import com.assignment.taxiCom.entity.Booking;
import com.assignment.taxiCom.entity.Invoice;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Integer> {

    Page<Invoice> findAll(Pageable pageable);

    @Query(value = "Select * from invoice i where i.dateCreated >= ?1 and i.dateCreated <= ?2", nativeQuery = true)
    Page<Invoice> filterInvoiceByPeriod(ZonedDateTime startDay, ZonedDateTime endDay, Pageable pageable);

    @Query(value ="Select sum(totalCharge) from invoice i where i.customerId = ?1 and i.dateCreated >= ?2 and i.dateCreated <=?3",nativeQuery = true )
    double getCustomerRevenueByPeriod(long customerId,ZonedDateTime startDay, ZonedDateTime endDay);

    @Query(value ="Select sum(totalCharge) from invoice i where i.driverId = ?1 and i.dateCreated >= ?2 and i.dateCreated <=?3",nativeQuery = true )
    double getDriverRevenueByPeriod(long driverId,ZonedDateTime startDay, ZonedDateTime endDay);

    @Query(value ="Select * from invoice i where i.customerId = ?1 and i.dateCreated >= ?2 and i.dateCreated <=?3",nativeQuery = true)
    Page<Invoice> getCustomerInvoiceByPeriod(long customerId,ZonedDateTime startDay, ZonedDateTime endDay,Pageable pageable);

    @Query(value ="Select * from invoice i where i.driverId = ?1 and i.dateCreated >= ?2 and i.dateCreated <=?3",nativeQuery = true)
    Page<Invoice> getDriverInvoiceByPeriod(long driverId,ZonedDateTime startDay, ZonedDateTime endDay,Pageable pageable);
}
