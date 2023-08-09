package com.moon42.kh;

import java.sql.*;

public class DatabaseConnector implements AutoCloseable {
    private Connection connection;
    private PreparedStatement insertStatement;

    @Override
    public void close() throws SQLException {
        insertStatement.close();
        connection.close();
    }

    public void insert(TransactionLogItem tli) {
        System.out.println("Inserting " + tli);
        try {
            insertStatement.setInt(1, tli.getId());
            insertStatement.setString(2, tli.getAccountNumber());
            insertStatement.setString(3, tli.getAccountName());
            insertStatement.setTimestamp(4, Timestamp.from(tli.getEntryDate().toInstant()));
            insertStatement.setDate(5, Date.valueOf(tli.getValueDate()));
            insertStatement.setString(6, tli.getCounterAccountNumber());
            insertStatement.setString(7, tli.getPartner());
            insertStatement.setBigDecimal(  8, tli.getAmount());
            insertStatement.setString(9, tli.getCurrency());
            insertStatement.setString(10, tli.getNotice());
            insertStatement.setString(11, tli.getTransactionBankId());
            insertStatement.setString(12, tli.getTransactionTypeCode());
            insertStatement.setString(13, tli.getTransactionTypeName());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConnector connect(String jdbcUrl, String user, String password) throws SQLException {
        DatabaseConnector dc = new DatabaseConnector();
        dc.connection = DriverManager.getConnection(jdbcUrl, user, password);
        dc.insertStatement = dc.connection.prepareStatement(
                "insert into TRANSACTIONS (" +
                        "ID, ACCOUNT_NUMBER, ACCOUNT_NAME, ENTRY_DATE, VALUE_DATE, COUNTER_ACCOUNT_NUMBER, PARTNER, " +
                        "AMOUNT, CURRENCY, NOTICE, TRANSACTION_BANK_ID, TRANSACTION_TYPE_CODE, TRANSACTION_TYPE_NAME" +
                        ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        return dc;
    }
}
