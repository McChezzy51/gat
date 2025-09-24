public class HashTester {
    public static void main(String[] args) {
        String fileContents = FileIO.readFile("README.md");
        System.out.println(fileContents);
        System.out.println(gat.SHA1(fileContents));
    }
}
