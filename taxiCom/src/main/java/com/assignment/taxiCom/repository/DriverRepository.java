package com.assignment.taxiCom.repository;

import com.assignment.taxiCom.entity.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends PagingAndSortingRepository<Driver, Integer> {
    Page<Driver> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM driver D WHERE D.license = ?1", nativeQuery = true)
    Page<Driver> findDriverByLicense(String license, Pageable pageable);

    @Query(value = "select * from driver d where d.rating = ?1", nativeQuery = true)
    Page<Driver> findDriverByRating(int rating, Pageable pageable);

    @Query(value = "select * from driver d where d.phoneNumber = ?1", nativeQuery = true)
    Page<Driver> findDriverByPhone(String phoneNumber, Pageable pageable);
}
