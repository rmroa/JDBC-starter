package com.rm.jdbc.starter;

import com.rm.jdbc.starter.custom.Blob;
import com.rm.jdbc.starter.custom.Flights;
import com.rm.jdbc.starter.custom.GeneratedKeys;
import com.rm.jdbc.starter.custom.MetaData;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException, IOException {

        Flights flights = new Flights();
        flights.getFlightsBetween(LocalDate.of(2020, 1, 1).atStartOfDay(), LocalDateTime.now());

        MetaData metaData = new MetaData();
        metaData.checkMetaData();

        GeneratedKeys generatedKeys = new GeneratedKeys();
        generatedKeys.returnGeneratedKeys();

        Blob blob = new Blob();
        blob.getImage();
    }
}
