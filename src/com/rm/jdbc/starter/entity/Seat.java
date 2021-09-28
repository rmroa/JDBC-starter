package com.rm.jdbc.starter.entity;

public class Seat {

    private Aircraft aircraft;
    private String seatNo;

    public Seat() {
    }


    public Seat(Aircraft aircraft, String seatNo) {
        this.aircraft = aircraft;
        this.seatNo = seatNo;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "aircraft=" + aircraft +
                ", seatNo='" + seatNo + '\'' +
                '}';
    }
}
