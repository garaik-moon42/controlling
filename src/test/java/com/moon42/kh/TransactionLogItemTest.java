package com.moon42.kh;

public class TransactionLogItemTest {

    public static void main(String[] args) {
        TransactionLogItem tli = TransactionLogItem.createOf(
                "\"10401000-50526784-50831051\";" +
                "\"Vállalati devizaszámla\";" +
                "\"2023-03-01T07:01:49.023+01:00\";" +
                "\"2023-03-01\";" +
                "\"10401000-50526784-50831006\";" +
                "\"MOON42 RDI Kft.\";" +
                "\"            45,65\";" +
                "\"USD\";" +
                "\"-\";" +
                "\"010030301H074274\";" +
                "\"504\";" +
                "\"Fedezet átvezetése inkasszóhoz\""
        );
        System.out.println(tli);
        System.out.println("Hash code: " + tli.hashCode());
    }
}
