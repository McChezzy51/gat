import java.io.File;

public class BLOBTester {
    public static void main(String[] args) {
        // Make the BLOB
        gat.createBLOB("README.md");

        // Make sure the BLOB has the right file name and contents
        String fileContents = FileIO.readFile("README.md");
        String fileHash = gat.SHA1(fileContents);
        String fileName = "git/objects/" + fileHash;
        File file = new File("git/objects/" + fileHash);
        if (file.exists()) {
            if (FileIO.readFile(fileName).equals(fileContents)) {
                System.out.println("Successfully created BLOB file!");
            }
        }

        // Now delete the BLOB to keep our repository clean
        if (file.delete()) {
            System.out.println("Successfully deleted BLOB file!");
        }
    }
}
