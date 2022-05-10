package com.assignment.taxiCom.repository;

import com.assignment.taxiCom.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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

    @Query(value = "Select * from car C\n" +
                    "Where C.id in (SELECT C2.id\n" +
                        "From car C2, driver D1\n" +
                        "Where D1.car_id = C2.id \n" +
                        "and C2.id not in (SELECT C1.id\n" +
                            "FROM booking B, invoice I, driver D, car C1\n" +
                            "Where I.id = B.invoiceId and I.driverId = D.id and D.car_id = C1.id\n" +
                            "and ((?1 between B.pickUpTime and B.dropOffTime) or (?2 between B.pickUpTime and B.dropOffTime))))",
            nativeQuery = true)
    Page<Car> getAvailableForBooking(LocalDateTime pickUp, LocalDateTime dropOff, Pageable pageable);

}
