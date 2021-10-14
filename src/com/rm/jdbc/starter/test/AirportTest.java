package com.rm.jdbc.starter.test;

import com.rm.jdbc.starter.dao.AirportDao;
import com.rm.jdbc.starter.entity.Airport;

import java.util.List;
import java.util.Optional;

public class AirportTest {

    public static void main(String[] args) {
        checkFindAllAirport();
    }

    private static void checkFindAllAirport() {
        AirportDao airportDao = AirportDao.getInstance();
        List<Airport> airports = airportDao.findAll();
        System.out.println(airports);
    }

    private static void checkFindByIdAirport() {
        AirportDao airportDao = AirportDao.getInstance();
        Optional<Airport> maybeAirport = airportDao.findById("MNK");
        System.out.println(maybeAirport);
    }

    private static void checkSaveAirport() {
        AirportDao airportDao = AirportDao.getInstance();
        Airport airport = new Airport(
                "MBK",
                "Беларусь",
                "Брест"
        );
        Airport save = airportDao.save(airport);
        System.out.println(save);
    }

    private static void checkUpdateAirport() {
        AirportDao airportDao = AirportDao.getInstance();
        Optional<Airport> maybeAirport = airportDao.findById("MBK");
        System.out.println(maybeAirport);

        maybeAirport.ifPresent(airport -> {
            airport.setCountry("Россия");
            airport.setCity("Москва");
            airportDao.update(airport);
        });
    }

    private static void checkDeleteAirport() {
        AirportDao airportDao = AirportDao.getInstance();
        boolean deleteResult = airportDao.delete("MBK");
        System.out.println(deleteResult);
    }
}
