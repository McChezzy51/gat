import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class gat {
    private static boolean compressData = false;

    public static void setCompressData(boolean shouldCompressData) {
        compressData = shouldCompressData;
    }

    public static boolean getCompressData() {
        return compressData;
    }

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

    // Gets the SHA-1 hash for the string dataToHash
    public static String SHA1(String dataToHash) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            // Get SHA-1 byte array
            digest.update(dataToHash.getBytes("UTF-8"), 0, dataToHash.length());
            // Convert byte array to hexadecimal
            return byteArrayToHex(digest.digest());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String byteArrayToHex(byte[] byteArray) {
        // Use a StringBuilder to optimize performance
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            byte num = byteArray[i];
            // Hexadecimal is a base-16 number system. It takes 4 bits to have a range of 16 numbers.
            // A byte has 8 bits. Thus, each byte contains two hexadecimal characters' worth of data.
            char[] hexDigits = new char[2];
            // The >> operation takes all the bits in the byte and shifts them down 4 spots.
            // For instance, 10010110 becomes 00001001.
            // The & operation takes all the bits and compares them one by one to 0xF (corresponding to the bits 00001111).
            // Only if a bit is 1 and the corresponding bit is 1 in 0xF, will the resulting byte have a 1 in that position.
            // This effectively sets the first four bits to 0, as we only care about the last 4.
            // For example: 10010110 & 0xF = 00000110
            // Character.forDigit turns the digit into a character in any number system; again, hexadecimal is base 16.
            hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
            // We have already converted the first 4 bits into hex; now, we convert the second 4 bits.
            hexDigits[1] = Character.forDigit((num & 0xF), 16);
            String curString = new String(hexDigits);
            sb.append(curString);
        }
        return sb.toString();
    }

    // Creates a BLOB for the file in the given filePath and stores it in git/objects
    // The BLOB's name is the SHA-1 hash of the file's contents
    // The BLOB's contents are identical to the file's contents
    public static void createBLOB(String filePath) {
        String fileContents = FileIO.readFile(filePath);
        if (fileContents == null) {
            System.out.println("Failed to read file!");
            return;
        }
        String fileHash = SHA1(fileContents);
        if (fileHash == null) {
            System.out.println("Failed to hash file contents!");
            return;
        }
        if (compressData) {
            compressData(filePath, fileHash);
        } else {
            if (!FileIO.writeToFile("git/objects/" + fileHash, fileContents)) {
                System.out.println("Failed to write to file!");
                return;
            }
        }
    }

    // Adds the file referenced by filePath to the index
    public static void stageFile(String filePath) {
        String fileContents = FileIO.readFile(filePath);
        String fileHash = SHA1(fileContents);
        String dataToAppend = "";
        // If the index is empty, we don't want to begin with a newline
        if (!FileIO.readFile("git/index").equals("")) {
            dataToAppend += "\n";
        }
        // Format is [hash] [file_name]
        dataToAppend += fileHash + " " + filePath;
        FileIO.appendToFile("git/index", dataToAppend);
    }
    
    // Compresses a file using ZLIB compression
    // QUESTION: should the file's hash be generated on the compressed or the uncompressed data??
    public static void compressData(String inputFile, String outputFile) {
        try {
            String zipFileName = "git/objects/" + outputFile;

            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);

            zos.putNextEntry(new ZipEntry(outputFile));

            byte[] bytes = Files.readAllBytes(Paths.get(inputFile));
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
            zos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
