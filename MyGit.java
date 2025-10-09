import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class MyGit {

    public static void init() {
        gat.initializeRepo();
        commit("Sydney Assil", "initial commit");
    }

    public static void stage(String filePath) {
        gat.createBLOB(filePath);
        gat.stageFile(filePath);
    }

    public static void commit(String author, String message) {
        gat.createTree("git/index");
        File commit = new File("commit");
        FileIO.writeToFile("commit", "tree: " + gat.createTree("git/index")
                + "\nparent: " + FileIO.readFile("git/HEAD")
                + "\nauthor: " + author
                + "\ndate: " + LocalDate.now()
                + "\nmessage: " + message);
        String sha1 = gat.SHA1(FileIO.readFile("commit"));
        FileIO.writeToFile("git/HEAD", sha1);
        gat.createBLOB("commit");
        commit.delete();
    }

    public static void checkout(String sha1) {
        File commit = new File("git/objects/" + sha1);
        
    }
}
