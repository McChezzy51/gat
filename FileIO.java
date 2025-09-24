import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIO {
    // Makes a directory within the current working directory (usually gat/).
    // Returns true if succeeded in making the directory and false if it failed (i.e. the directory already existed).
    public static boolean makeDirectory(String directoryName) {
        File dir = new File(directoryName);
        if (dir.exists()) {
            return false;
        }
        return dir.mkdir();
    }

    // Makes the specified file in the current working directory (usually gat/).
    // Returns true if succeeded
    public static boolean makeFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Writes the specified content to the file referenced by fileName
    // If there is no such file with name fileName, the file is first created
    // Returns true if succeeded in writing the data, false otherwise
    public static boolean writeToFile(String fileName, String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Reads the specified file
    // Returns the file's contents, or null if reading failed
    public static String readFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}