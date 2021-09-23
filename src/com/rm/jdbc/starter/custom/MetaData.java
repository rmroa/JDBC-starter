package com.rm.jdbc.starter.custom;

import com.rm.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetaData {

    public void checkMetaData() throws SQLException {

        try (Connection connection = ConnectionManager.get()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                String catalog = catalogs.getString(1);
                ResultSet schemas = metaData.getSchemas();
                while (schemas.next()) {
                    String schema = schemas.getString("TABLE_SCHEM");
                    ResultSet tables = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"});
                    if (schema.equals("public")) {
                        while (tables.next()) {
                            String tableName = tables.getString("TABLE_NAME");
                            System.out.println("* " + tableName + " *");
                            ResultSet columns = metaData.getColumns(catalog, schema, tableName, "%");
                            while (columns.next()) {
                                System.out.println(columns.getString("COLUMN_NAME"));
                            }
                        }
                    }
                }
            }
        }
    }
}
