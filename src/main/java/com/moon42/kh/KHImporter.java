package com.moon42.kh;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class KHImporter {

    private static Stream<String> getFileContentAsStream() throws IOException {
        Path sourceFilePath = Paths.get("./sample/kh-transaction-history-sample.csv");
        Charset latin2 = Charset.forName("ISO-8859-2");
        return Files.lines(sourceFilePath, latin2);
    }

    public static void main(String[] args) {
        try (
            DatabaseConnector db = DatabaseConnector.connect("jdbc:mysql://localhost:3306/kh?useSSL=false&allowPublicKeyRetrieval=true", "root", "notroot");
            Stream<String> headerStream = getFileContentAsStream();
            Stream<String> contentStream = getFileContentAsStream().skip(1)
        ) {
            String header = headerStream.findFirst().orElseThrow();
            Stream<String> linesWithoutHeaders = contentStream.filter(Predicate.not(Predicate.isEqual(header)));
            linesWithoutHeaders.map(TransactionLogItem::createOf).forEach(db::insert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
