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

    @Query(value = "select * from driver d where d.id = ?1", nativeQuery = true)
    Driver findDriverById(long id);

    @Query(value = "SELECT * FROM driver D WHERE D.licenseNumber = ?1", nativeQuery = true)
    Driver findDriverByLicense(String license);

    @Query(value = "select * from driver d where d.rating = ?1", nativeQuery = true)
    Page<Driver> findDriverByRating(int rating, Pageable pageable);

    @Query(value = "select * from driver d where d.phoneNumber = ?1", nativeQuery = true)
    Driver findDriverByPhone(String phoneNumber);

    @Query(value = "select * from driver d where d.car_id = ?1", nativeQuery = true)
    Driver findDriverByCar(long carId);
}
