package sample.files;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;


public class FileWorker {

    private volatile static FileWorker fileWorker;

    final static private File tftpDir = new File("C:\\tmp");

    private static FileInputStream fileInputStream;
    private static FileOutputStream fileOutputStream;

    private FileWorker(){}

    public static FileWorker getFileWorker() {

        if (fileWorker == null) {
            synchronized (FileWorker.class) {
                if (fileWorker == null) {
                    fileWorker = new FileWorker();
                }
            }
        }
        return fileWorker;
    }

    public ArrayList<String> getCurrentDirFilesNames() {

        ArrayList<String> namesArray = new ArrayList<>();

        File[] filesList = tftpDir.listFiles();

        if (filesList != null) {
            for (File itrFile : filesList) {

                if (itrFile.isFile()) namesArray.add(itrFile.getName());
            }
        }
        else {
            System.err.println("No files in directory!");
        }

        return namesArray;
    }



    public void writeToFile(byte[] dataToFile, String destFile) {

//        initOutputStream(getFile(destFile));

        Path pathToFile = getFileAbsPath(destFile);
        try {
            if (!Files.exists(pathToFile, LinkOption.NOFOLLOW_LINKS))
                Files.createFile(pathToFile);
            Files.write(pathToFile, dataToFile, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            fileOutputStream.write(dataToFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public byte[] readFromFile(String fileName) {

        Path pathToFile = getFileAbsPath(fileName);
        try {
            return Files.readAllBytes(pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Path getFileAbsPath(String fileName) {

        return Paths.get(tftpDir.getAbsolutePath() + "\\" + fileName);
    }

    private static void initOutputStream(File file){

        try {
            fileOutputStream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void initInputStream(File file) {

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
