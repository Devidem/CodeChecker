package experiments;

import com.google.common.io.Files;
import io.qameta.allure.Step;

import java.io.File;
import java.io.IOException;


/**
 * Класс содержащий методы для работы с файлами
 */
public class FileManager {
    /**
     * Копирует файл sourcePath в targetPath
     */
    @Step("Копирование файла")
    public static void copy (String sourcePath, String targetPath) {
        try {
            Files.copy(new File(sourcePath), new File(targetPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Перемещает categories.json из ./experiments в ./target/allure-results
     */
    public static void copyCategories () {
        try {
            new File("./target/allure-results").mkdirs();
            Files.copy(new File("./src/main/java/experiments/categories.json"), new File("./target/allure-results/categories.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
