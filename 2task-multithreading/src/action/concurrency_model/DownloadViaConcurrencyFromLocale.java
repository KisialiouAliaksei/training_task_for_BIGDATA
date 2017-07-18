package action.concurrency_model;

import utils.parser.UselessParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static constant.Constants.DOWNLOAD_DIR;
import static constant.Constants.SERVER_DIR;

/**
 * Created by Aliaksei_Kisialiou on 7/18/2017.
 */
public class DownloadViaConcurrencyFromLocale implements DownloadViaConcurrency {
    public static AtomicInteger countFinishedDownloads = new AtomicInteger();
    private final String fileForDownload;
    public final Lock downloadLock = new ReentrantLock();


    public DownloadViaConcurrencyFromLocale(String fileForDownload)
    {
        this.fileForDownload = fileForDownload;
    }


    @Override
    public DownloadViaConcurrency call() {
        download(fileForDownload);
        countFinishedDownloads.incrementAndGet();
        return this;
    }

    @Override
    public void download(String fileForDownload) {
        File serverFile = new File(SERVER_DIR + fileForDownload);
        downloadLock.lock();
        try {
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

        } finally {
            downloadLock.unlock();
        }
    }

    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
