package utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Alex on 19.07.2017.
 */
public class Generator {
    public static void generateFiles(String dir,int number) {

        for (int i = 0; i < number;i++ ) {
            File file = new File(dir + i + ".txt");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void cleanDir(String dir) {
        File file = new File(dir);
        for (File myFile : file.listFiles()) {
            if (myFile.isFile()) myFile.delete();
        }
    }
}
