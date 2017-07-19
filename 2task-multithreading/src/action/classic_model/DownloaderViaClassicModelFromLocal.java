package action.classic_model;

import manager.ManagerViaClassicModel;
import utils.parser.UselessParser;

import java.io.*;
import java.nio.file.Files;

import static constant.Constants.DOWNLOAD_DIR;
import static constant.Constants.SERVER_DIR;

/**
 * Created by Aliaksei_Kisialiou on 7/17/2017.
 */
public class DownloaderViaClassicModelFromLocal implements DownloadViaClassicModel {
    private final String fileForDownload;

    public DownloaderViaClassicModelFromLocal(String fileForDownload) {
        this.fileForDownload = fileForDownload;
    }


    @Override
    public void run() {
        synchronized (ManagerViaClassicModel.class) {
            download(fileForDownload);
            ManagerViaClassicModel.countFinishedDownload++;
        }
    }

    @Override
    public void download(String fileForDownload) {
        File serverFile = new File(SERVER_DIR + fileForDownload);
            if (serverFile.exists()) {
                File downloadFile = new File(DOWNLOAD_DIR + fileForDownload);
                File serverFileAfterParse = UselessParser.parseAndCopyTXT(serverFile);
                try {
                    copy(serverFileAfterParse,downloadFile);
                    System.out.println("File " + serverFile.getName() + " downloaded successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("File " + serverFile.getName() + " not found");
            }
    }
    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

}
