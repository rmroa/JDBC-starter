package com.rm.jdbc.starter.custom;

import com.rm.jdbc.starter.util.ConnectionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Blob {

    public void getImage() throws SQLException, IOException {
        String sql = "SELECT image " +
                "FROM aircraft " +
                "WHERE id = ?";

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                byte[] image = resultSet.getBytes("image");
                Files.write(Path.of("resources", "boeing777new.jpg"), image, StandardOpenOption.CREATE);
            }
        }
    }

    public void saveImage() throws SQLException, IOException {
        String sql = "UPDATE aircraft " +
                "SET image = ? " +
                "WHERE id = 1";
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBytes(1, Files.readAllBytes(Path.of("resources", "boeing777.jpg")));
            preparedStatement.executeUpdate();
        }
    }

//    private static void saveImage() throws SQLException, IOException {
//        String sql = "UPDATE aircraft " +
//                "SET image = ? " +
//                "WHERE id = 1";
//        try (Connection connection = ConnectionManager.open();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            connection.setAutoCommit(false);
//            Blob blob = connection.createBlob();
//            blob.setBytes(1, Files.readAllBytes(Path.of("resources", "boeing777.jpg")));
//
//            preparedStatement.setBlob(1, blob);
//            preparedStatement.executeUpdate();
//            connection.commit();
//        }
//    }
}
