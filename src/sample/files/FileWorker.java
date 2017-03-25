package sample.files;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Sir Lskyp on 25-Mar-17.
 */
public class FileWorker {

    final private File tftpDir = new File("C:\\tftpTmp");

    public ArrayList<String> getFilesNames() {

        ArrayList<String> namesArray = new ArrayList<>();

        File[] filesList = this.tftpDir.listFiles();

        assert filesList != null;
        for (File itrFile : filesList) {

            if (itrFile.isFile()) namesArray.add(itrFile.getName());
        }

        return namesArray;
    }
}
