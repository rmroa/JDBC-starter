package com.rm.jdbc.starter.dao;

import com.rm.jdbc.starter.entity.Airport;
import com.rm.jdbc.starter.exception.DaoException;
import com.rm.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AirportDao implements Dao<String, Airport> {

    private static final AirportDao INSTANCE = new AirportDao();
    private static final String FIND_ALL_SQL = "" +
            "SELECT code, " +
            "country, " +
            "city " +
            "FROM airport";
    private static final String FIND_BY_ID_SQL = "" +
            "SELECT code, " +
            "country, " +
            "city " +
            "FROM airport " +
            "WHERE code = ?";
    private static final String SAVE_SQL = "" +
            "INSERT INTO airport (code, country, city) " +
            "VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "" +
            "UPDATE airport " +
            "SET country = ?, " +
            "city = ? " +
            "WHERE code = ?";
    private static final String DELETE_SQL = "" +
            "DELETE FROM airport " +
            "WHERE code = ?";

    public static AirportDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Airport> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Airport> airports = new ArrayList<>();
            while (resultSet.next()) {
                airports.add(buildAirport(resultSet));
            }
            return airports;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<Airport> findById(String code) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            Airport airport = new Airport();
            if (resultSet.next()) {
                airport = buildAirport(resultSet);
            }
            return Optional.ofNullable(airport);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Airport save(Airport airport) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, airport.getCode());
            preparedStatement.setString(2, airport.getCountry());
            preparedStatement.setString(3, airport.getCity());
            preparedStatement.executeUpdate();

            return airport;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void update(Airport airport) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, airport.getCountry());
            preparedStatement.setString(2, airport.getCity());
            preparedStatement.setString(3, airport.getCode());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public boolean delete(String code) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setString(1, code);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Airport buildAirport(ResultSet resultSet) throws SQLException {
        return new Airport(
                resultSet.getString("code"),
                resultSet.getString("country"),
                resultSet.getString("city")
        );
    }
}
