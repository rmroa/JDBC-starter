package com.rm.jdbc.starter.dao;

import com.rm.jdbc.starter.dto.TicketDto;
import com.rm.jdbc.starter.entity.Ticket;
import com.rm.jdbc.starter.exception.DaoException;
import com.rm.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class TicketDao implements Dao<Long, Ticket> {

    private static final TicketDao INSTANCE = new TicketDao();
    private static final String FIND_ALL_SQL = "" +
            "SELECT ticket.id, " +
            "passenger_no, " +
            "passenger_name, " +
            "flight_id, " +
            "seat_no, " +
            "cost, " +
            "f.flight_no, " +
            "f.departure_date, " +
            "f.departure_airport_code, " +
            "f.arrival_date, " +
            "f.arrival_airport_code, " +
            "f.aircraft_id, " +
            "f.status " +
            "FROM ticket " +
            "JOIN flight f " +
            "on f.id = ticket.flight_id";
    private static final String FIND_BY_ID_SQL = "" +
            FIND_ALL_SQL +
            " WHERE ticket.id = ?";
    private static final String SAVE_SQL = "" +
            "INSERT INTO ticket (passenger_no, passenger_name, flight_id, seat_no, cost) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "" +
            "UPDATE ticket " +
            "SET passenger_no = ?, " +
            "passenger_name = ?, " +
            "flight_id = ?, " +
            "seat_no = ?, " +
            "cost = ? " +
            "WHERE id = ?";
    private static final String DELETE_SQL = "" +
            "DELETE FROM ticket " +
            "WHERE id = ?";

    private final FlightDao flightDao = FlightDao.getInstance();


    private TicketDao() {
    }

    public List<Ticket> findAll(TicketDto ticketDto) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (ticketDto.getSeatNo() != null) {
            whereSql.add("seat_no LIKE ?");
            parameters.add("%" + ticketDto.getSeatNo() + "%");
        }
        if (ticketDto.getPassengerName() != null) {
            whereSql.add("passenger_name = ?");
            parameters.add(ticketDto.getPassengerName());
        }
        parameters.add(ticketDto.getLimit());
        parameters.add(ticketDto.getOffset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ? "));

        String sql = FIND_ALL_SQL + where;

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<Ticket> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<Ticket> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Ticket ticket = null;
            if (resultSet.next()) {
                ticket = buildTicket(resultSet);
            }
            return Optional.ofNullable(ticket);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Ticket save(Ticket ticket) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ticket.getPassengerNo());
            preparedStatement.setString(2, ticket.getPassengerName());
            preparedStatement.setLong(3, ticket.getFlight().getId());
            preparedStatement.setString(4, ticket.getSeatNo());
            preparedStatement.setBigDecimal(5, ticket.getCost());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                ticket.setId(generatedKeys.getLong("id"));
            }
            return ticket;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public void update(Ticket ticket) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, ticket.getPassengerNo());
            preparedStatement.setString(2, ticket.getPassengerName());
            preparedStatement.setLong(3, ticket.getFlight().getId());
            preparedStatement.setString(4, ticket.getSeatNo());
            preparedStatement.setBigDecimal(5, ticket.getCost());
            preparedStatement.setLong(6, ticket.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);

        }
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    private Ticket buildTicket(ResultSet resultSet) throws SQLException {
//        Flight flight = new Flight(
//                resultSet.getLong("id"),
//                resultSet.getString("flight_no"),
//                resultSet.getTimestamp("departure_date").toLocalDateTime(),
//                resultSet.getString("departure_airport_code"),
//                resultSet.getTimestamp("arrival_date").toLocalDateTime(),
//                resultSet.getString("arrival_airport_code"),
//                resultSet.getInt("aircraft_id"),
//                resultSet.getString("status")
//        );
        return new Ticket(
                resultSet.getLong("id"),
                resultSet.getString("passenger_no"),
                resultSet.getString("passenger_name"),
//                flight,
                flightDao.findById(resultSet.getLong("flight_id"),
                        resultSet.getStatement().getConnection()).orElse(null),
                resultSet.getString("seat_no"),
                resultSet.getBigDecimal("cost")
        );
    }
}
