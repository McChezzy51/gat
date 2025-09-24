public class HashTester {
    public static void main(String[] args) {
        System.out.println(FileIO.readFile("README.md"));
        System.out.println(gat.SHA1("README.md"));
    }
}
