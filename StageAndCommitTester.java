import java.io.*;

public class StageAndCommitTester {
    public static void main(String[] args) throws IOException {
        
        IndexTester.resetRepository();
        GitWrapper git = new GitWrapper();
        
        git.init();

        //Checking if stage works on a file in the working directory
        String syd = "Sydney";
        File s = new File(syd);
        if (!s.exists()) {
            s.createNewFile();
        }
        FileIO.writeToFile(syd, "the is the sydney file");
        git.stage(syd);
        git.commit("Sydney Assil", "First commit");


        //Checking if stage works on a file inside a folder
        String peeps = "People";
        File p = new File(peeps);
        if (!p.exists()) {
            p.mkdir();
        }
        String joe = "People/Joe";
        File j = new File(joe);
        if (!j.exists()) {
            j.createNewFile();
        }
        FileIO.writeToFile(joe, "the is the joe file");
        git.stage(joe);
        git.commit("Sydney Assil", "Second commit");
        
    }

}
