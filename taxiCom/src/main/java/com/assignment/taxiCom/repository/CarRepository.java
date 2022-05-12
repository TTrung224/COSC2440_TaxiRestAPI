package com.assignment.taxiCom.repository;

import com.assignment.taxiCom.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;


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


    @Query(value = "select distinct C.licensePlate as car_license, count(distinct t2) as day_used " +
            "from car C, booking B, invoice I, Driver D, generate_series(cast(b.pickUpTime as date) ,  cast(b.dropOffTime as date), cast('1 day'as interval)) t, cast(t as date) t2 " +
            "where C.id = D.car_id and D.id = I.driverID and I.id = B.invoiceId " +
            "and DATE_PART('month', t2) = ?1 and DATE_PART('year', t2) = ?2 " +
            "group by C.licensePlate", nativeQuery = true)
    Page<Map<String, Integer>> getUsage(int month, int year, Pageable pageable);

    @Query(value = "SELECT * From car C2\n" +
                   "Where C2.id in (SELECT C1.id\n" +
                        "From car C1, driver D1\n" +
                        "Where C1.id = D1.car_id and C1.id not in (SELECT C.id\n" +
                            "From booking B, invoice I, driver D, car C\n" +
                            "Where I.id = B.invoiceId and I.driverId = D.id and D.car_id = C.id\n" +
                            "And ?1 <= B.dropOffTime))",
            nativeQuery = true)
    Page<Car> getAvailableForBooking(LocalDateTime pickUp, Pageable pageable);
}
