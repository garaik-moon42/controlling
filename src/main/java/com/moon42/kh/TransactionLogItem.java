package com.moon42.kh;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TransactionLogItem {
    private int id;
    private String accountNumber;
    private String accountName;
    private ZonedDateTime entryDate;
    private LocalDate valueDate;
    private String counterAccountNumber;
    private String partner;
    private BigDecimal amount;
    private String currency;
    private String notice;
    private String transactionBankId;
    private String transactionTypeCode;
    private String transactionTypeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public String getCounterAccountNumber() {
        return counterAccountNumber;
    }

    public void setCounterAccountNumber(String counterAccountNumber) {
        this.counterAccountNumber = counterAccountNumber;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getTransactionBankId() {
        return transactionBankId;
    }

    public void setTransactionBankId(String transactionBankId) {
        this.transactionBankId = transactionBankId;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionLogItem)) return false;
        TransactionLogItem that = (TransactionLogItem) o;
        return accountNumber.equals(that.accountNumber)
                && entryDate.equals(that.entryDate)
                && partner.equals(that.partner)
                && amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, entryDate, partner, amount);
    }

    @Override
    public String toString() {
        return "TransactionLogItem{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountName='" + accountName + '\'' +
                ", entryDate=" + entryDate +
                ", valueDate=" + valueDate +
                ", counterAccountNumber='" + counterAccountNumber + '\'' +
                ", partner='" + partner + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", notice='" + notice + '\'' +
                ", transactionBankId='" + transactionBankId + '\'' +
                ", transactionTypeCode='" + transactionTypeCode + '\'' +
                ", transactionTypeName='" + transactionTypeName + '\'' +
                '}';
    }

    public static TransactionLogItem createOf(String exportCSVLine) {
        Iterator<String> values =
                Arrays.stream(exportCSVLine.split(";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
                .map(v -> v.substring(1, v.length() - 1))
                .toList().iterator();// ToDo: extract separator character as global setting
        TransactionLogItem tli = new TransactionLogItem();
        tli.setAccountNumber(values.next());
        tli.setAccountName(values.next());
        tli.setEntryDate(ZonedDateTime.parse(values.next()));
        tli.setValueDate(LocalDate.parse(values.next()));
        tli.setCounterAccountNumber(values.next());
        tli.setPartner(values.next());
        tli.setAmount(new BigDecimal(values.next().trim().replaceAll(",", ".")));
        tli.setCurrency(values.next().toUpperCase());
        tli.setNotice(values.next().trim().replaceAll(" {2,}", " "));
        tli.setTransactionBankId(values.next());
        tli.setTransactionTypeCode(values.next());
        tli.setTransactionTypeName(values.next());
        tli.setId(Objects.hash(tli.getAccountNumber(), tli.getEntryDate(), tli.getPartner(), tli.getAmount()));
        return tli;
    }
}

