package com.rm.jdbc.starter.custom;

import com.rm.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Flights {

    public List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {

        String sql = "SELECT id" +
                " FROM flight" +
                " WHERE departure_date BETWEEN ? AND ?";

        List<Long> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setFetchSize(50);
            preparedStatement.setQueryTimeout(10);
            preparedStatement.setMaxRows(100);

            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getLong("id"));
            }
        }
        return result;
    }
}