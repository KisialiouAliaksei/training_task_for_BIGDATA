package action.concurrency_model;

import manager.Manager;
import utils.parser.UselessParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static constant.Constants.*;

/**
 * Created by Aliaksei_Kisialiou on 7/18/2017.
 */
public class UploadViaConcurrencyToLocale implements UploadViaConcurrency {
    public static AtomicInteger countFinishedUpload = new AtomicInteger();
    private final String fileForUpload;
    public final Lock uploadLock = new ReentrantLock();

    public UploadViaConcurrencyToLocale(String fileForDownload)
    {
        this.fileForUpload = fileForDownload;
    }

    @Override
    public UploadViaConcurrency call() throws Exception {
        upload(fileForUpload);
        countFinishedUpload.incrementAndGet();
        return this;
    }

    @Override
    public void upload(String fileName) {
        File localeFile = new File(UPLOAD_DIR + fileName);
        uploadLock.lock();
        try{
            if (localeFile.exists()) {
                File serverFile = new File(SERVER_DIR + fileName);
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
        } finally {
            uploadLock.unlock();
        }
    }
    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

}
