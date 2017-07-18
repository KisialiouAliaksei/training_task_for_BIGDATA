package utils;

import constant.FileType;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static constant.Constants.*;



/**
 * Created by Aliaksei_Kisialiou on 7/17/2017.
 */
public final class GenerateAndDeleteFilesUtils {

    public static void generateFiles(String dir,int number) {
        for (int i = 0; i <= number;i++ ) {
            File file = new File(dir + i + ".txt");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void generateFiles(String dir,FileType fileType, int number) {
        for (int i = 0; i <= number;i++ ) {
            File file = new File(dir + i  + DOT + fileType);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateFilesWithSelectionType(String dir,FileType fileType, String ... args) {
        for (int i = 0; i <= args.length;i++ ) {
            File file = new File(dir + args[i] + DOT + fileType);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateFilesWithoutSelectionType(String dir, String ... args) {
        for (int i = 0; i <= args.length;i++ ) {
            File file = new File(dir + args[i]);
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
    public static String[] generateFileName(int number, FileType fileType) {
        String[] strings = new String[number];
        for (int i = 0; i < strings.length; i++ ) {
            strings[i] = i + DOT + fileType;
        }
        return strings;
    }

}


