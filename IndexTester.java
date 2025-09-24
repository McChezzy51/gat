import java.io.FileReader;

import java.io.BufferedReader;

public class IndexTester {
    public static void main(String[] args) {
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
}
