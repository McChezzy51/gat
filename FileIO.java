import java.io.File;
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
}