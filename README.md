# gat
## FileIO.java
Provides a few helper functions to easily work with directories and files.
## gat.java
Provides git-related functions, like initializing the repository (`void initializeRepo()`), generating a file's hash code (`String SHA1(String filePath)`), and some helper functions (`String byteArrayToHex(byte[] byteArray)`). The `SHA1()` function works by utilizing `java.security.MessageDigest` to generate a SHA-1 code in the form of a byte array, then converting that array into its hexadecimal representation in string form.

`void stageFile(String filePath)` will add a file name and its hash to the git index file for staging.
## InitializationTester.java
This is a tester for repository initialization functionality. **Run the main function in this file to run the tester.** It repeatedly creates and deletes the git repository to ensure that the initialization function works properly. Finally, it attempts to initialize the repository twice in a row to ensure that it properly detects that the repository already exists.
## HashTester.java
A small file to ensure that our SHA-1 hashing function works properly.
## BLOBTester.java
This file creates a BLOB for README.md, checks its contents against the original README.md, then deletes the BLOB to test our BLOB generation functions.
## IndexTester.java
First, the `main` function in this file runs `void resetRepository()`, which deletes all BLOB and test .txt files and clears the index file. Then it creates a few test .txt files, stages them, generates their blobs, then checks the index file and ensures the index file points to the correct BLOBs and original files. It compares the contents of the BLOBs and the original .txt files to ensure they are the same.