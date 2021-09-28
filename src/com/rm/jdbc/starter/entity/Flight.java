package com.rm.jdbc.starter.entity;

import java.time.LocalDateTime;

public class Flight {

    private Long id;
    private String flightNo;
    private LocalDateTime departureDate;
    private String departureAirportCode;
    private LocalDateTime arrivalDate;
    private String arrivalAirportCOde;
    private Integer aircraftId;
    private String status;

    public Flight(Long id, String flightNo, LocalDateTime departureDate, String departureAirportCode,
                  LocalDateTime arrivalDate, String arrivalAirportCOde, Integer aircraftId, String status) {
        this.id = id;
        this.flightNo = flightNo;
        this.departureDate = departureDate;
        this.departureAirportCode = departureAirportCode;
        this.arrivalDate = arrivalDate;
        this.arrivalAirportCOde = arrivalAirportCOde;
        this.aircraftId = aircraftId;
        this.status = status;
    }

    public Flight(String flightNo, LocalDateTime departureDate, String departureAirportCode, LocalDateTime arrivalDate,
                  String arrivalAirportCOde, Integer aircraftId, String status) {
        this.flightNo = flightNo;
        this.departureDate = departureDate;
        this.departureAirportCode = departureAirportCode;
        this.arrivalDate = arrivalDate;
        this.arrivalAirportCOde = arrivalAirportCOde;
        this.aircraftId = aircraftId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalAirportCOde() {
        return arrivalAirportCOde;
    }

    public void setArrivalAirportCOde(String arrivalAirportCOde) {
        this.arrivalAirportCOde = arrivalAirportCOde;
    }

    public Integer getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Integer aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightNo='" + flightNo + '\'' +
                ", departureDate=" + departureDate +
                ", departureAirportCode='" + departureAirportCode + '\'' +
                ", arrivalDate=" + arrivalDate +
                ", arrivalAirportCOde='" + arrivalAirportCOde + '\'' +
                ", aircraftId=" + aircraftId +
                ", status='" + status + '\'' +
                '}';
    }
}
