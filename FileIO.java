import java.io.File;

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

    }
}