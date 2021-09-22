package com.rm.jdbc.starter;

import com.rm.jdbc.starter.custom.Flights;
import com.rm.jdbc.starter.custom.GeneratedKeys;
import com.rm.jdbc.starter.custom.MetaData;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {

        Flights flights = new Flights();
        flights.getFlightsBetween(LocalDate.of(2020, 1, 1).atStartOfDay(), LocalDateTime.now());

        MetaData metaData = new MetaData();
        metaData.checkMetaData();

        GeneratedKeys generatedKeys = new GeneratedKeys();
        generatedKeys.returnGeneratedKeys();
    }
}
