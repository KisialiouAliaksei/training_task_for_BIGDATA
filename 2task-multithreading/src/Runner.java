import manager.ManagerViaClassicModel;
import constant.FileType;
import utils.GenerateAndDeleteFilesUtils;

import static constant.Constants.*;


/**
 * Created by Aliaksei_Kisialiou on 7/17/2017.
 */
public class Runner {
    public static final int NUMBER_FILES_FOR_CREATE = 1000;
    public static String[] fileNamesForDownload;
    public static String[] fileNamesForUpload;

    public static void main(String[] args) {

        generateAndClear();

        ManagerViaClassicModel classicModel = new ManagerViaClassicModel();
        classicModel.openSession();
        long start = System.currentTimeMillis();
        classicModel.startUpload(fileNamesForUpload);
        classicModel.startDownload(fileNamesForDownload);
        classicModel.closeSession();
        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        System.out.println(timeConsumedMillis);

    }

    private static void generateAndClear() {
        GenerateAndDeleteFilesUtils.cleanDir(SERVER_DIR);
        GenerateAndDeleteFilesUtils.cleanDir(UPLOAD_DIR);
        GenerateAndDeleteFilesUtils.cleanDir(DOWNLOAD_DIR);
        GenerateAndDeleteFilesUtils.generateFiles(SERVER_DIR, FileType.TXT, NUMBER_FILES_FOR_CREATE);
        GenerateAndDeleteFilesUtils.generateFiles(UPLOAD_DIR, FileType.MP3, NUMBER_FILES_FOR_CREATE);
        fileNamesForDownload =  GenerateAndDeleteFilesUtils.generateFileName(NUMBER_FILES_FOR_CREATE, FileType.TXT);
        fileNamesForUpload =  GenerateAndDeleteFilesUtils.generateFileName(NUMBER_FILES_FOR_CREATE, FileType.MP3);
    }





}
