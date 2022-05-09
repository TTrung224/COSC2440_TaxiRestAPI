package com.assignment.taxiCom.repository;

import com.assignment.taxiCom.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository  extends PagingAndSortingRepository<Car, Integer> {
    Page<Car> findAll(Pageable pageable);

    @Query(value = "select * from car c where c.id = ?1", nativeQuery = true)
    Page<Car> findCarById(long id, Pageable pageable);

    @Query(value = "select * from car c where c.vin = ?1", nativeQuery = true)
    Page<Car> findCarByVin(String vin, Pageable pageable);

    @Query(value = "select * from car c where c.color = ?1", nativeQuery = true)
    Page<Car> findCarByColor(String color, Pageable pageable);

    @Query(value = "select * from car c where c.licensePlate = ?1", nativeQuery = true)
    Page<Car> findCarByLicensePlate(String license, Pageable pageable);

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
}
