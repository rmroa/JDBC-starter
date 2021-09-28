package com.rm.jdbc.starter.test;

import com.rm.jdbc.starter.dao.FlightDao;
import com.rm.jdbc.starter.dao.SeatDao;
import com.rm.jdbc.starter.entity.Aircraft;
import com.rm.jdbc.starter.entity.Flight;
import com.rm.jdbc.starter.entity.Seat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class FlightTest {

    public static void main(String[] args) {

        checkDeleteFlight();
    }

    private static void checkFindAllFlight() {
        FlightDao flightDao = FlightDao.getInstance();
        List<Flight> flights = flightDao.findAll();
        System.out.println(flights);
    }

    private static void checkFindByIdFlight() {
        FlightDao flightDao = FlightDao.getInstance();
        Optional<Flight> flight = flightDao.findById(1L);
        System.out.println(flight);
    }

    private static void checkSaveFlight() {
        FlightDao flightDao = FlightDao.getInstance();
        Flight flight = new Flight(
                "NewFlightNoo",
                LocalDateTime.now(),
                "MNK",
                LocalDateTime.now(),
                "LDN",
                1,
                "ARRIVED"
        );
        Flight save = flightDao.save(flight);
        System.out.println(save);
    }

    private static void checkUpdateFlight() {
        FlightDao flightDao = FlightDao.getInstance();
        Optional<Flight> maybeFlight = flightDao.findById(11L);
        System.out.println(maybeFlight);

        maybeFlight.ifPresent(flight -> {
            flight.setAircraftId(4);
            flightDao.update(flight);
        });
    }

    private static void checkDeleteFlight() {
        FlightDao flightDao = FlightDao.getInstance();
        boolean delete = flightDao.delete(10L);
        System.out.println(delete);
    }
}
