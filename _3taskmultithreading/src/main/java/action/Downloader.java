package action;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Aliaksei_Kisialiou on 7/19/2017.
 */
public class Downloader implements Callable<Downloader> {
    public static final String SERVER_DIR = "C:/server/";
    public static final String DOWNLOAD_DIR = "C:/download/";
    public static final String UPLOAD_DIR = "C:/upload/";
    public final Lock downloadLock = new ReentrantLock();
    private final String fileForDownload;

    public Downloader(String fileForDownload) {
        this.fileForDownload = fileForDownload;
    }

    public Downloader call() throws Exception {
        download(fileForDownload);
        return null;
    }

    public void download(String fileForDownload) {
        File serverFile = new File(SERVER_DIR + fileForDownload);
        downloadLock.lock();
        try {
            if (serverFile.exists()) {
                File downloadFile = new File(DOWNLOAD_DIR + fileForDownload);
                try {
                    copy(serverFile,downloadFile);
                    System.out.println("File " + serverFile.getName() + " downloaded successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("File " + serverFile.getName() + " not found");
            }

        } finally {
            downloadLock.unlock();
        }
    }

    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
