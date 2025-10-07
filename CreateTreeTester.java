import java.io.*;
import java.security.NoSuchAlgorithmException;

public class CreateTreeTester {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        System.out.println("=== Running CreateTreeTester ===");
        createTreeTester();
    }

    public static void createTreeTester() throws IOException, NoSuchAlgorithmException {
        Git.cleanUp();

        // setup directory
        String baseDir = "./git/demoDir";
        File mainDir = new File(baseDir);
        if (!mainDir.exists()) {
            mainDir.mkdir();
        }

        String[] fileNames = { "a.txt", "b.txt" };
        String[] fileContents = {
            "a text content",
            "b text content"
        };

        for (int i = 0; i < fileNames.length; i++) {
            File f = new File(baseDir + "/" + fileNames[i]);
            f.createNewFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write(fileContents[i]);
            }
            Git.makeBlob(baseDir + "/" + fileNames[i]);
            Git.updateIndex(new File(baseDir + "/" + fileNames[i]));
        }

        String treeSHA = Git.createTree("./git/index");

        if (treeSHA != null) {
            File treeFile = new File("./git/objects/" + treeSHA);
            if (treeFile.exists()) {
                System.out.println("Tree creation successful → " + treeSHA);
            } else {
                System.out.println("Tree file missing from objects folder.");
            }
        } else {
            System.out.println("createTree() returned null — tree not created.");
        }
    }
}
