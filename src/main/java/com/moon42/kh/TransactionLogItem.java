package com.moon42.kh;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public class TransactionLogItem {
    private int id;
    private String accountNumber;
    private String accountName;
    private ZonedDateTime entryDate;
    private LocalDate valueDate;
    private LocalDate month;
    private String counterAccountNumber;
    private String partner;
    private BigDecimal amount;
    private String currency;
    private Optional<String> ctrlCategory = Optional.empty();
    private Optional<LocalDate> ctrlMonth = Optional.empty();
    private Optional<Boolean> ctrlInclude = Optional.empty();
    private Optional<Boolean> ctrlVat = Optional.empty();
    private Optional<BigDecimal> ctrlAmount = Optional.empty();
    private String notice;
    private String transactionBankId;
    private String transactionTypeCode;
    private String transactionTypeName;
    private Optional<String> ctrlnvoiceUrl = Optional.empty();

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

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
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

    public Optional<String> getCtrlCategory() {
        return ctrlCategory;
    }

    public void setCtrlCategory(Optional<String> ctrlCategory) {
        this.ctrlCategory = ctrlCategory;
    }

    public Optional<LocalDate> getCtrlMonth() {
        return ctrlMonth;
    }

    public void setCtrlMonth(Optional<LocalDate> ctrlMonth) {
        this.ctrlMonth = ctrlMonth;
    }

    public Optional<Boolean> getCtrlInclude() {
        return ctrlInclude;
    }

    public void setCtrlInclude(Optional<Boolean> ctrlInclude) {
        this.ctrlInclude = ctrlInclude;
    }

    public Optional<Boolean> getCtrlVat() {
        return ctrlVat;
    }

    public void setCtrlVat(Optional<Boolean> ctrlVat) {
        this.ctrlVat = ctrlVat;
    }

    public Optional<BigDecimal> getCtrlAmount() {
        return ctrlAmount;
    }

    public void setCtrlAmount(Optional<BigDecimal> ctrlAmount) {
        this.ctrlAmount = ctrlAmount;
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

    public Optional<String> getCtrlnvoiceUrl() {
        return ctrlnvoiceUrl;
    }

    public void setCtrlnvoiceUrl(Optional<String> ctrlnvoiceUrl) {
        this.ctrlnvoiceUrl = ctrlnvoiceUrl;
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
        LocalDate valueDate = LocalDate.parse(values.next());
        tli.setValueDate(valueDate);
        tli.setMonth(valueDate.withDayOfMonth(1));
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

