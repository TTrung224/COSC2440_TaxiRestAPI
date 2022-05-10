package com.assignment.taxiCom.repository;

import com.assignment.taxiCom.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public interface CarRepository  extends PagingAndSortingRepository<Car, Integer> {
    Page<Car> findAll(Pageable pageable);

    @Query(value = "select * from car c where c.id = ?1", nativeQuery = true)
    Car findCarById(long id);

    @Query(value = "select * from car c where c.vin = ?1", nativeQuery = true)
    Car findCarByVin(String vin);

    @Query(value = "select * from car c where c.color = ?1", nativeQuery = true)
    Page<Car> findCarByColor(String color, Pageable pageable);

    @Query(value = "select * from car c where c.licensePlate = ?1", nativeQuery = true)
    Car findCarByLicensePlate(String license);

    @Query(value = "select * from car c where c.convertible = ?1", nativeQuery = true)
    Page<Car> findCarByConvertible(boolean convertible, Pageable pageable);

    @Query(value = "select * from car c where c.rating = ?1", nativeQuery = true)
    Page<Car> findCarByRating(int rating, Pageable pageable);

    @Query(value = "select * from car c where c.make = ?1", nativeQuery = true)
    Page<Car> findCarByMake(String make, Pageable pageable);

    @Query(value = "select * from car c where c.model = ?1", nativeQuery = true)
    Page<Car> findCarByModel(String model, Pageable pageable);

    @Query(value = "select * from car c where c.ratePerKilometer = ?1", nativeQuery = true)
    Page<Car>  findCarByRate(int ratePerKilometer, Pageable pageable);

    @Query(value = "select * from car c where c.id not in (select car_id from driver)", nativeQuery = true)
    Page<Car> getAvailable(Pageable pageable);

    @Query(value = "select distinct C.licensePlate, SUM(DATE_PART('day', b.dropOffTime - b.pickUpTime)) AS day_used from car C, booking B, invoice I, Driver D where C.id = D.car_id and D.id = I.driverID and I.id = B.invoiceId group by C.licensePlate", nativeQuery = true)
    Page<List> getUsage(Pageable pageable);
}
