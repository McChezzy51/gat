public class gat {
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
}
