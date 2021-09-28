package com.rm.jdbc.starter.test;

import com.rm.jdbc.starter.dao.SeatDao;
import com.rm.jdbc.starter.dto.SeatDto;
import com.rm.jdbc.starter.entity.Aircraft;
import com.rm.jdbc.starter.entity.Seat;

import java.util.List;
import java.util.Optional;

public class SeatTest {

    public static void main(String[] args) {
        checkSaveSeat();
    }

    private static void checkFindAllWithParamSeat() {
        SeatDto seatDto = new SeatDto(3, 0, 1);
        SeatDao seatDao = SeatDao.getInstance();
        List<Seat> seats = seatDao.findAll(seatDto);
        System.out.println(seats);
    }

    private static void checkFindAllSeat() {
        SeatDao seatDao = SeatDao.getInstance();
        List<Seat> seats = seatDao.findAll();
        System.out.println(seats);
    }

    private static void checkFindByIdSeat() {
        SeatDao seatDao = SeatDao.getInstance();
        Aircraft aircraft = new Aircraft(1);
        Seat seat = new Seat(aircraft, "A12");
        Optional<Seat> maybeSeat = seatDao.findById(seat);
        System.out.println(maybeSeat);
    }

    private static void checkSaveSeat() {
        SeatDao seatDao = SeatDao.getInstance();
        Aircraft aircraft = new Aircraft(1);
        Seat seat = new Seat(aircraft, "A1");
        Seat saveSeat = seatDao.save(seat);
        System.out.println(saveSeat);
    }

    private static void checkUpdateSeat() {
        SeatDao seatDao = SeatDao.getInstance();
        Aircraft aircraft = new Aircraft(1);
        Seat resultSeat = new Seat(aircraft, "A17");
        Optional<Seat> maybeSeat = seatDao.findById(resultSeat);
        System.out.println(maybeSeat);

        maybeSeat.ifPresent(seat -> {
            seat.setSeatNo("A22");
            seatDao.update(seat);
        });
    }

    private static void checkDeleteSeat() {
        SeatDao seatDao = SeatDao.getInstance();
        Aircraft aircraft = new Aircraft(1);
        Seat seat = new Seat(aircraft, "A22");
        boolean deleteResult = seatDao.delete(seat);
        System.out.println(deleteResult);
    }
}
