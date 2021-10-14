package com.rm.jdbc.starter.dao;

import com.rm.jdbc.starter.custom.Blob;
import com.rm.jdbc.starter.entity.Aircraft;
import com.rm.jdbc.starter.exception.DaoException;
import com.rm.jdbc.starter.util.ConnectionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AircraftDao implements Dao<Integer, Aircraft> {

    private static final AircraftDao INSTANCE = new AircraftDao();
    private static final String FIND_ALL_SQL = "" +
            "SELECT " +
            "id, " +
            "model, " +
            "image " +
            "FROM aircraft";
    private static final String FIND_BY_ID_SQL = "" +
            "SELECT id, " +
            "model, " +
            "image " +
            "FROM aircraft " +
            "WHERE id = ?";
    private static final String SAVE_SQL = "" +
            "INSERT INTO aircraft (model) " +
            "VALUES (?)";
    private static final String UPDATE_SQL = "" +
            "UPDATE aircraft " +
            "SET model = ? " +
            "WHERE id = ?";
    private static final String DELETE_SQL = "" +
            "DELETE FROM aircraft " +
            "WHERE id = ?";

    public static AircraftDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Aircraft> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Aircraft> aircrafts = new ArrayList<>();
            while (resultSet.next()) {
                aircrafts.add(buildAircraft(resultSet));
            }
            return aircrafts;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<Aircraft> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Aircraft aircraft = null;
            if (resultSet.next()) {
                aircraft = buildAircraft(resultSet);
            }
            return Optional.ofNullable(aircraft);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Aircraft save(Aircraft aircraft) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, aircraft.getModel());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                aircraft.setId(generatedKeys.getInt("id"));
            }
            return aircraft;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void update(Aircraft aircraft) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, aircraft.getModel());
            preparedStatement.setInt(2, aircraft.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private Aircraft buildAircraft(ResultSet resultSet) throws SQLException {
        return new Aircraft(
                resultSet.getInt("id"),
                resultSet.getString("model"),
                resultSet.getBlob("image")
        );
    }
}
