package com.rm.jdbc.starter.dao;

import com.rm.jdbc.starter.dto.SeatDto;
import com.rm.jdbc.starter.entity.Aircraft;
import com.rm.jdbc.starter.entity.Seat;
import com.rm.jdbc.starter.exception.DaoException;
import com.rm.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class SeatDao implements Dao<Seat, Seat> {

    private static final SeatDao INSTANCE = new SeatDao();
    private static final String FIND_ALL_SQL = "" +
            "SELECT seat.aircraft_id, " +
            "seat.seat_no, " +
            "a.image, " +
            "a.id, " +
            "a.model " +
            "FROM seat " +
            "JOIN aircraft a " +
            "on a.id = seat.aircraft_id";
    private static final String FIND_BY_AIRCRAFT_ID_AND_SEAT_NO = "" +
            "SELECT aircraft_id, " +
            "seat_no, " +
            "a.image, " +
            "a.id, " +
            "a.model " +
            "FROM seat " +
            "JOIN aircraft a " +
            "on a.id = seat.aircraft_id " +
            "WHERE aircraft_id = ? " +
            "AND seat_no = ?";
    private static final String SAVE_SQL = "" +
            "INSERT INTO seat (aircraft_id, seat_no) " +
            "VALUES (?, ?)";
    private static final String UPDATE_SQL = "" +
            "UPDATE seat " +
            "SET seat_no = ? " +
            "WHERE aircraft_id = ? " +
            "AND seat_no = ?";
    private static final String DELETE_SQL = "" +
            "DELETE FROM seat " +
            "WHERE aircraft_id = ? " +
            "AND seat_no = ?";

    public List<Seat> findAll(SeatDto seatDto) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (seatDto.getAircraftId() != null) {
            whereSql.add("aircraft_id = ?");
            parameters.add(seatDto.getAircraftId());
        }
        parameters.add(seatDto.getLimit());
        parameters.add(seatDto.getOffset());
        String where = whereSql.stream()
                .collect(joining("", " WHERE ", " LIMIT ? OFFSET ?"));

        String sql = FIND_ALL_SQL + where;

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Seat> seats = new ArrayList<>();
            while (resultSet.next()) {
                seats.add(buildSeat(resultSet));
            }
            return seats;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<Seat> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Seat> seats = new ArrayList<>();
            while (resultSet.next()) {
                seats.add(buildSeat(resultSet));
            }
            return seats;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<Seat> findById(Seat seat) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_AIRCRAFT_ID_AND_SEAT_NO)) {
            preparedStatement.setInt(1, seat.getAircraft().getId());
            preparedStatement.setString(2, seat.getSeatNo());

            ResultSet resultSet = preparedStatement.executeQuery();
            Seat seatResult = null;
            if (resultSet.next()) {
                seatResult = buildSeat(resultSet);
            }
            return Optional.ofNullable(seatResult);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Seat save(Seat seat) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setInt(1, seat.getAircraft().getId());
            preparedStatement.setString(2, seat.getSeatNo());

            preparedStatement.executeUpdate();
            return seat;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public void update(Seat seat) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, seat.getSeatNo());
            preparedStatement.setInt(2, seat.getAircraft().getId());
            preparedStatement.setObject(3, "A17");

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public boolean delete(Seat seat) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, seat.getAircraft().getId());
            preparedStatement.setString(2, seat.getSeatNo());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static SeatDao getInstance() {
        return INSTANCE;
    }

    private Seat buildSeat(ResultSet resultSet) throws SQLException {
        Aircraft aircraft = new Aircraft(
                resultSet.getBlob(1),
                resultSet.getInt("id"),
                resultSet.getString("model")
        );
        return new Seat(
                aircraft,
                resultSet.getString("seat_no")
        );
    }
}
