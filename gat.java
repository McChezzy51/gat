public class gat {
    public static void initializeRepo() {
        if (!FileIO.makeDirectory("git")) {
            System.out.println("Git Repository Already Exists");
            return;
        }
        // If the git directory didn't exist already, git/objects didn't either -- we don't need to check
        FileIO.makeDirectory("git/objects");
        FileIO.makeDirectory("git/index");
    }
}
