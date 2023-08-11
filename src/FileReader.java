import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileReader {
    static ArrayList<String> readFileContents(String fileName) {
        Path path = Path.of("./resources/" + fileName);
        try {
            return new ArrayList<>(Files.readAllLines(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом.");
            return new ArrayList<>();
        }
    }

    static boolean FileIsExist(String fileName) {
        Path path = Path.of("./resources/" + fileName);
        return Files.exists(path);
    }
}