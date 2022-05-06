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

    @Query(value = "Select * from invoice i where i.dateCreated ==?1", nativeQuery = true)
    Page<Invoice> filterInvoiceByDate(ZonedDateTime date, Pageable pageable);
}
