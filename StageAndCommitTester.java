import java.io.*;

public class StageAndCommitTester {
    public static void main(String[] args) throws IOException {
        
        //Checking if stage works on a file in the working directory
        String syd = "Sydney";
        File s = new File(syd);
        if (!s.exists()) {
            s.createNewFile();
        }
        FileIO.writeToFile(syd, "the is the sydney file");
        Git.stage(syd);

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
        Git.stage(joe);
        Git.commit("Sydney Assil", "First commit");

        IndexTester.resetRepository();
        //TO-DO TALK TO THEISS: 
        //Different sha1 for commits...
        //Should I get rid of "working" and fix format of tree storing? + Comitting isn't storing files...
        

    }

}
