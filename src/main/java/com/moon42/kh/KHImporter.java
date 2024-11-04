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
        Path sourceFilePath = Paths.get("./src/main/resources/sample/HISTORY_EQ24CT2S_2024-11-04T17_22_50.csv");
        Charset latin2 = Charset.forName("ISO-8859-2");
        return Files.lines(sourceFilePath, latin2);
    }

    public static void main(String[] args) {
        try (
            DatabaseConnector db = DatabaseConnector.connect("jdbc:mysql://localhost:3306/kh?useSSL=false&allowPublicKeyRetrieval=true", "root", "notroot");
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
