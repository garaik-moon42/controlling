CREATE TABLE TRANSACTIONS
(
    ID                     INT PRIMARY KEY,
    ACCOUNT_NUMBER         VARCHAR(26)    NOT NULL,
    ACCOUNT_NAME           VARCHAR(64)    NOT NULL,
    ENTRY_DATE             DATETIME       NOT NULL, -- Note: ZonedDateTime's zone details will be lost.
    VALUE_DATE             DATE           NOT NULL,
    COUNTER_ACCOUNT_NUMBER VARCHAR(34),             -- 34 characters are the upper limit of IBAN bank account numbers.
    PARTNER                VARCHAR(100),
    AMOUNT                 DECIMAL(20, 2) NOT NULL, -- You can adjust precision (20) and scale (2) as needed.
    CURRENCY               VARCHAR(3)     NOT NULL,
    NOTICE                 VARCHAR(200),
    TRANSACTION_BANK_ID    VARCHAR(32),
    TRANSACTION_TYPE_CODE  VARCHAR(3),
    TRANSACTION_TYPE_NAME  VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
