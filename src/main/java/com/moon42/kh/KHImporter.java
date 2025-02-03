package com.moon42.kh;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class KHImporter {

    private static Stream<String> getFileContentAsStream() throws IOException {
        Path sourceFilePath = Paths.get("./HISTORY_2025.csv");
        Charset latin2 = Charset.forName("ISO-8859-2");
        return Files.lines(sourceFilePath, latin2);
    }

    public static void main(String[] args) {
        try (
            DatabaseConnector db = DatabaseConnector.connect("jdbc:mysql://79.172.252.61:3306/rditools_erp?useSSL=false&allowPublicKeyRetrieval=true", "rditools_erproot", "sl7fn.[mmYm!");
            Stream<String> headerStream = getFileContentAsStream();
            Stream<String> contentStream = getFileContentAsStream().skip(1)
        ) {
            Set<Integer> existingIds = db.getStoredItemIds();
            String header = headerStream.findFirst().orElseThrow();
            Stream<String> linesWithoutHeaders = contentStream.filter(Predicate.not(Predicate.isEqual(header)));
            linesWithoutHeaders.map(TransactionLogItem::createOf).filter(item -> !existingIds.contains(item.getId())).forEach(db::insert);
            System.out.println("Number of inserted rows: " + db.getInsertCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
