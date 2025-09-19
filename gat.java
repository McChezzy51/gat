import java.security.MessageDigest;

public class gat {
    // Creates all the necessary files and directories in ./git/
    public static void initializeRepo() {
        boolean madeGit = FileIO.makeDirectory("git");
        boolean madeObjects = FileIO.makeDirectory("git/objects");
        boolean madeIndex = FileIO.makeFile("git/index");
        boolean madeHEAD = FileIO.makeFile("git/HEAD");
        if (!madeGit && !madeObjects && !madeIndex && !madeHEAD) {
            System.out.println("Git Repository Already Exists");
            return;
        }
        System.out.println("Git Repository Created");
    }

    // Retrieve's a file's contents and hashes them
    public static String SHA1(String filePath) {
        String fileContents = FileIO.readFile(filePath);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(fileContents.getBytes("UTF-8"), 0, fileContents.length());
        }
    }
}
