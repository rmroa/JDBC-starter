package com.rm.jdbc.starter.custom;

import com.rm.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneratedKeys {

    public void returnGeneratedKeys() throws SQLException {

        String sql = "INSERT INTO info (data)" +
                " VALUES " +
                "('autogenerated')";

        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt("id");
                System.out.println(generatedId);
            }
        }
    }
}
