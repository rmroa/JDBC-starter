package com.rm.jdbc.starter.dto;

public class TicketDto {

    private Integer limit;
    private Integer offset;
    private String passengerName;
    private String seatNo;

    public TicketDto(Integer limit, Integer offset, String passengerName, String seatNo) {
        this.limit = limit;
        this.offset = offset;
        this.passengerName = passengerName;
        this.seatNo = seatNo;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getSeatNo() {
        return seatNo;
    }
}
