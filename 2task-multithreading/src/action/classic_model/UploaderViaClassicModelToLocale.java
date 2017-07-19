package action.classic_model;

import manager.ManagerViaClassicModel;
import utils.parser.UselessParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static constant.Constants.SERVER_DIR;
import static constant.Constants.UPLOAD_DIR;

/**
 * Created by Aliaksei_Kisialiou on 7/17/2017.
 */
public class UploaderViaClassicModelToLocale implements UploadViaClassicModel {
    private final String fileForUpload;

    public UploaderViaClassicModelToLocale(String fileForDownload) {
        this.fileForUpload = fileForDownload;
    }

    @Override
    public void run() {
        synchronized (ManagerViaClassicModel.class) {
            upload(fileForUpload);
            ManagerViaClassicModel.countFinishedUploads++;
        }
    }

    @Override
    public void upload(String file) {
        File localeFile = new File(UPLOAD_DIR + file);
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
    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
