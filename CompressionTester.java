public class CompressionTester {
    public static void main(String[] args) {
        IndexTester.resetRepository();


        String fileName = "README.md";
        gat.setCompressData(true);
        gat.createBLOB(fileName);
    }
}
