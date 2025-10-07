import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    // NOTE: any data previously in the file will be deleted!
    // If there is no such file with name fileName, the file is first created
    // Returns true if succeeded in writing the data, false otherwise
    public static boolean writeToFile(String fileName, String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(content);
            bufferedWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Appends the specified content to the file referenced by fileName
    // NOTE: any data previously in the file will NOT be deleted!
    // If there is no such file with name fileName, the file is first created
    // Returns true if succeeded in writing the data, false otherwise
    public static boolean appendToFile(String fileName, String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            bufferedWriter.write(content);
            bufferedWriter.close();
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

    // builds the working list from the index file and writes it to git/working
    public static List<String> buildWorkingList(File indexFile) throws IOException {
        List<String> lines = Files.readAllLines(indexFile.toPath());
        List<String> workingList = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(" ");
            if (parts.length < 2) continue;

            String fileName = parts[0];
            String sha = parts[1];
            workingList.add("blob " + sha + " " + fileName);
        }

        Files.write(Paths.get("git/working"), workingList);
        return workingList;
    }

    // groups working list entries by their parent directories
    public static Map<String, List<String>> groupByParentDirectory(List<String> workingList) {
        Map<String, List<String>> dirMap = new HashMap<>();

        for (String entry : workingList) {
            String[] parts = entry.split(" ");
            if (parts.length < 3) continue;

            String fullPath = parts[2];
            File f = new File(fullPath);
            String parent = (f.getParent() == null) ? "" : f.getParent();

            dirMap.computeIfAbsent(parent, k -> new ArrayList<>()).add(entry);
        }

        return dirMap;
    }

    // sorts directories from deepest to shallowest for recursive tree creation
    public static List<String> sortDirectoriesByDepth(Map<String, List<String>> dirMap) {
        List<String> dirs = new ArrayList<>(dirMap.keySet());
        dirs.sort((a, b) -> Integer.compare(b.split("/").length, a.split("/").length));
        return dirs;
    }

    // writes a tree file to git/objects and returns its SHA1 hash
    public static String writeTreeObject(String content) throws IOException {
        String sha = gat.SHA1(content);
        Path path = Paths.get("git/objects", sha);
        Files.write(path, content.getBytes());
        return sha;
    }
}
