package com.rm.jdbc.starter.dto;

public class SeatDto {

    private Integer limit;
    private Integer offset;
    private Integer aircraftId;

    public SeatDto(Integer limit, Integer offset, Integer aircraftId) {
        this.limit = limit;
        this.offset = offset;
        this.aircraftId = aircraftId;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getAircraftId() {
        return aircraftId;
    }
}
