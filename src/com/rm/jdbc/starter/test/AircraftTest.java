package com.rm.jdbc.starter.test;

import com.rm.jdbc.starter.dao.AircraftDao;
import com.rm.jdbc.starter.entity.Aircraft;

import java.util.List;
import java.util.Optional;

public class AircraftTest {

    public static void main(String[] args) {
        checkFindAllAircraft();
    }

    private static void checkFindAllAircraft() {
        AircraftDao aircraftDao = AircraftDao.getInstance();
        List<Aircraft> aircrafts = aircraftDao.findAll();
        System.out.println(aircrafts);
    }

    private static void checkFindByIdAircraft() {
        AircraftDao aircraftDao = AircraftDao.getInstance();
        Optional<Aircraft> aircraft = aircraftDao.findById(1);
        System.out.println(aircraft);
    }

    private static void checkSaveAircraft() {
        AircraftDao aircraftDao = AircraftDao.getInstance();
        Aircraft aircraft = new Aircraft("NewModel");
        Aircraft save = aircraftDao.save(aircraft);
        System.out.println(save);
    }

    private static void checkUpdateAircraft() {
        AircraftDao aircraftDao = AircraftDao.getInstance();
        Optional<Aircraft> maybeAircraft = aircraftDao.findById(5);
        System.out.println(maybeAircraft);

        maybeAircraft.ifPresent(aircraft -> {
            aircraft.setModel("UpdateModel");
            aircraftDao.update(aircraft);
        });
    }

    private static void checkDeleteAircraft() {
        AircraftDao aircraftDao = AircraftDao.getInstance();
        boolean deleteResult = aircraftDao.delete(5);
        System.out.println(deleteResult);
    }
}
