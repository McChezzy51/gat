import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;

public class IndexTester {
    public static void main(String[] args) {
        // First, clear everything
        resetRepository();

        // Create some files to stage
        FileIO.writeToFile("test1.txt", "ABC");
        FileIO.writeToFile("test2.txt", "DEF");
        FileIO.writeToFile("test3.txt", "GHI");

        // Stage our new files
        gat.stageFile("test1.txt");
        gat.stageFile("test2.txt");
        gat.stageFile("test3.txt");

        // Generate the BLOBs
        gat.createBLOB("test1.txt");
        gat.createBLOB("test2.txt");
        gat.createBLOB("test3.txt");

        // Verify our index is accurate
        try (BufferedReader br = new BufferedReader(new FileReader("git/index"))) {
            while (br.ready()) {
                String line = br.readLine();
                String[] components = line.split(" ");
                String fileHash = components[0];
                String fileName = components[1];
                String BLOBData = FileIO.readFile("git/objects/" + fileHash);
                String txtData = FileIO.readFile(fileName);
                if (BLOBData.equals(txtData)) {
                    System.out.println(fileName + " staged properly!");
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    // Deletes test files, object files, clears index file
    public static void resetRepository() {
        for (int i = 1; i <= 3; i++) {
            File curFile = new File("test" + i + ".txt");
            curFile.delete();
        }
        File objectsDir = new File("git/objects");
        for (File curObject : objectsDir.listFiles()) {
            // There shouldn't be any directories in git/objects, but let's be safe
            if (!curObject.isDirectory()) {
                curObject.delete();
            }
        }
        FileIO.writeToFile("git/HEAD", "");
        // Clear index file
        // A FileWriter will by default overwrite all existing contents with the new contents
        // Thus, writing an empty string will clear the file
        try (FileWriter fw = new FileWriter("git/index")) {
            fw.write("");
            fw.close();
        } catch (Exception e) {
            System.out.println("Failed to clear index file:");
            System.out.println(e.getMessage());
        }
    }
}
