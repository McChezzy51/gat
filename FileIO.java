import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

    // Reads the specified file
    // Returns the file's contents, or null if reading failed
    public static String readFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            while (br.ready()) {
                sb.append(br.readLine() + "\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}