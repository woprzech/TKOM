package tkom.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by wprzecho on 11.06.16.
 */
public class FileHelper {

    public static InputStream getInputStreamFromFile(final String filePath) {
        Path path = FileSystems.getDefault().getPath(filePath);
        final StringBuilder sb = new StringBuilder();
        try {
            Files.readAllLines(path).stream().forEach(line -> sb.append(line).append('\n'));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
    }
}
