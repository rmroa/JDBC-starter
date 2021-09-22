package com.rm.jdbc.starter.custom.transaction;

import java.sql.SQLException;

public class TransactionRunner {

    public static void main(String[] args) throws SQLException {

        Transaction transaction = new Transaction();
        transaction.transactionWithRollback();
    }
}
