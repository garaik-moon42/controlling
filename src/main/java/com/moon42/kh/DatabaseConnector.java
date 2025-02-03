package com.moon42.kh;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DatabaseConnector implements AutoCloseable {
    private static final String TRANSACTIONS_TABLE_NAME = "BANK_TRANSACTIONS";
    private Connection connection;
    private PreparedStatement insertStatement;
    private int insertCount = 0;

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
            insertStatement.setDate(6, Date.valueOf(tli.getMonth()));
            insertStatement.setString(7, tli.getCounterAccountNumber());
            insertStatement.setString(8, tli.getPartner());
            insertStatement.setBigDecimal(  9, tli.getAmount());
            insertStatement.setString(10, tli.getCurrency());
            insertStatement.setString(11, tli.getCtrlCategory().isEmpty() ? null : tli.getCtrlCategory().get());
            insertStatement.setDate(12, tli.getCtrlMonth().isEmpty() ? null : Date.valueOf(tli.getCtrlMonth().get()));
            insertStatement.setInt(13, tli.getCtrlInclude().isEmpty() ? -1 : (tli.getCtrlInclude().get()) ? 1 : 0);
            insertStatement.setInt(14, tli.getCtrlVat().isEmpty() ? -1 : (tli.getCtrlVat().get() ? 1 : 0));
            insertStatement.setBigDecimal(  15, tli.getCtrlAmount().isEmpty() ? null : tli.getCtrlAmount().get());
            insertStatement.setString(16, tli.getNotice());
            insertStatement.setString(17, tli.getTransactionBankId());
            insertStatement.setString(18, tli.getTransactionTypeCode());
            insertStatement.setString(19, tli.getTransactionTypeName());
            insertStatement.setString(20, tli.getCtrlnvoiceUrl().isEmpty() ? null : (tli.getCtrlnvoiceUrl().get()));
            insertStatement.executeUpdate();
            insertCount++;
        } catch (SQLException e) {
            throw new RuntimeException(e); // ToDo: not my favourite kind of expression handling
        }
    }

    public Set<Integer> getStoredItemIds() {
        Set<Integer> ids = new HashSet<>();
        try (ResultSet rs = connection.createStatement().executeQuery("select id from " + TRANSACTIONS_TABLE_NAME)) {
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // ToDo: not my favourite kind of expression handling
        }
        return ids;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public static DatabaseConnector connect(String jdbcUrl, String user, String password) throws SQLException {
        DatabaseConnector dc = new DatabaseConnector();
        dc.connection = DriverManager.getConnection(jdbcUrl, user, password);
        dc.insertStatement = dc.connection.prepareStatement(
                "insert into " + TRANSACTIONS_TABLE_NAME + " (" +
                        "ID, ACCOUNT_NUMBER, ACCOUNT_NAME, ENTRY_DATE, VALUE_DATE, MONTH, COUNTER_ACCOUNT_NUMBER, PARTNER, " +
                        "AMOUNT, CURRENCY, CTRL_CATEGORY, CTRL_MONTH, CTRL_INCLUDE, CTRL_VAT, CTRL_AMOUNT," +
                        "NOTICE, TRANSACTION_BANK_ID, TRANSACTION_TYPE_CODE, TRANSACTION_TYPE_NAME, CTRL_INVOICE_URL" +
                        ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        return dc;
    }
}
