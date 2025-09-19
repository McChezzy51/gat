import java.util.Comparator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InitializationTester {
    public static void main(String[] args) {
        gat.initializeRepo();
        cleanup();
        gat.initializeRepo();
        cleanup();
        gat.initializeRepo();
        gat.initializeRepo();
    }

    // Removes git directory
    public static void cleanup() {
        try {
            Files.walk(Paths.get("git"))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Git Repository Deleted");
    }
}
