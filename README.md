# gat
## FileIO.java
Provides a few helper functions to easily work with directories and files.
## gat.java
Provides git-related functions, like initializing the repository (`void initializeRepo()`), generating a file's hash code (`String SHA1(String filePath)`), and some helper functions (`String byteArrayToHex(byte[] byteArray)`). The `SHA1()` function works by utilizing `java.security.MessageDigest` to generate a SHA-1 code in the form of a byte array, then converting that array into its hexadecimal representation in string form.
## InitializationTester.java
This is a tester for repository initialization functionality. **Run the main function in this file to run the tester.** It repeatedly creates and deletes the git repository to ensure that the initialization function works properly. Finally, it attempts to initialize the repository twice in a row to ensure that it properly detects that the repository already exists.