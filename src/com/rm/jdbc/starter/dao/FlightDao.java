package com.rm.jdbc.starter.dao;

import com.rm.jdbc.starter.entity.Flight;
import com.rm.jdbc.starter.exception.DaoException;
import com.rm.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {

    private static final FlightDao INSTANCE = new FlightDao();
    private static final String FIND_ALL_SQL = "" +
            "SELECT id, " +
            "flight_no, " +
            "departure_date, " +
            "departure_airport_code, " +
            "arrival_date, " +
            "arrival_airport_code, " +
            "aircraft_id, " +
            "status " +
            "FROM flight";
    private static final String FIND_BY_ID_SQL = "" +
            "SELECT id, " +
            "flight_no, " +
            "departure_date, " +
            "departure_airport_code, " +
            "arrival_date, " +
            "arrival_airport_code, " +
            "aircraft_id, " +
            "status " +
            "FROM flight " +
            "WHERE id = ?";
    private static final String SAVE_SQL = "" +
            "INSERT INTO flight (flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "" +
            "UPDATE flight " +
            "SET flight_no = ?, " +
            "departure_date = ?, " +
            "departure_airport_code = ?, " +
            "arrival_date = ?, " +
            "arrival_airport_code = ?, " +
            "aircraft_id = ?, " +
            "status = ? " +
            "WHERE id = ?";
    private static final String DELETE_SQL = "" +
            "DELETE FROM flight " +
            "WHERE id = ?";

    private FlightDao() {
    }

    public List<Flight> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Flight> flights = new ArrayList<>();
            while (resultSet.next()) {
                flights.add(buildFlight(resultSet));
            }
            return flights;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<Flight> findById(Long id) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<Flight> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Flight flight = null;
            if (resultSet.next()) {
                flight = buildFlight(resultSet);
            }
            return Optional.ofNullable(flight);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Flight save(Flight flight) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, flight.getFlightNo());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
            preparedStatement.setString(3, flight.getDepartureAirportCode());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
            preparedStatement.setString(5, flight.getArrivalAirportCOde());
            preparedStatement.setInt(6, flight.getAircraftId());
            preparedStatement.setString(7, flight.getStatus());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                flight.setId(generatedKeys.getLong("id"));
            }
            return flight;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void update(Flight flight) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, flight.getFlightNo());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
            preparedStatement.setString(3, flight.getDepartureAirportCode());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
            preparedStatement.setString(5, flight.getArrivalAirportCOde());
            preparedStatement.setInt(6, flight.getAircraftId());
            preparedStatement.setString(7, flight.getStatus());
            preparedStatement.setLong(8, flight.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }

    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private Flight buildFlight(ResultSet resultSet) throws SQLException {
        return new Flight(
                resultSet.getLong("id"),
                resultSet.getString("flight_no"),
                resultSet.getTimestamp("departure_date").toLocalDateTime(),
                resultSet.getString("departure_airport_code"),
                resultSet.getTimestamp("arrival_date").toLocalDateTime(),
                resultSet.getString("arrival_airport_code"),
                resultSet.getInt("aircraft_id"),
                resultSet.getString("status")
        );
    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }
}
