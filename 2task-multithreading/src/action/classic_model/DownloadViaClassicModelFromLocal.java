package action.classic_model;

import utils.parser.UselessParser;

import java.io.*;
import java.nio.file.Files;

import static constant.Constants.DOWNLOAD_DIR;
import static constant.Constants.SERVER_DIR;

/**
 * Created by Aliaksei_Kisialiou on 7/17/2017.
 */
public class DownloadViaClassicModelFromLocal implements DownloadViaClassicModel {
    public volatile static int countFinishedDownloads = 0;
    private final String fileForDownload;

    public DownloadViaClassicModelFromLocal(String fileForDownload) {
        this.fileForDownload = fileForDownload;
    }


    @Override
    public void run() {
        download(fileForDownload);
        synchronized (DownloadViaClassicModelFromLocal.class) {
            countFinishedDownloads++;
        }
    }

    @Override
    public void download(String fileForDownload) {
        File serverFile = new File(SERVER_DIR + fileForDownload);
        synchronized (DownloadViaClassicModelFromLocal.class) {
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
    }
    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

}
