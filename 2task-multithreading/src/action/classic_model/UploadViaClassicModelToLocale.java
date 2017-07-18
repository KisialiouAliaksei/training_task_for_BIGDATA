package action.classic_model;

import utils.parser.UselessParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static constant.Constants.SERVER_DIR;
import static constant.Constants.UPLOAD_DIR;

/**
 * Created by Aliaksei_Kisialiou on 7/17/2017.
 */
public class UploadViaClassicModelToLocale implements UploadViaClassicModel {
    public volatile static int countFinishedUploads = 0;
    private final String fileForUpload;

    public UploadViaClassicModelToLocale(String fileForDownload) {
        this.fileForUpload = fileForDownload;
    }

    @Override
    public void run() {

        upload(fileForUpload);
        synchronized (UploadViaClassicModelToLocale.class) {
            countFinishedUploads++;
        }
    }

    @Override
    public void upload(String file) {
        File localeFile = new File(UPLOAD_DIR + file);
        synchronized (UploadViaClassicModelToLocale.class) {
            if (localeFile.exists()) {
                File serverFile = new File(SERVER_DIR + file);
                File localeFileAfterParse = UselessParser.parseAndCopyTXT(localeFile);
                try {
                    copy(localeFileAfterParse,serverFile);
                    System.out.println("File " + localeFile.getName() + " uploaded successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("File " + localeFile.getName() + " not found");
            }
        }
    }
    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
