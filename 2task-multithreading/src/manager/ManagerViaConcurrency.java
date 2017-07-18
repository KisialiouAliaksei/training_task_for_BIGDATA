package manager;

import action.concurrency_model.DownloadViaConcurrency;
import action.concurrency_model.DownloadViaConcurrencyFromLocale;
import action.concurrency_model.UploadViaConcurrency;
import action.concurrency_model.UploadViaConcurrencyToLocale;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


/**
 * Created by Aliaksei_Kisialiou on 7/18/2017.
 */
public class ManagerViaConcurrency implements Manager {
    private List<Callable<DownloadViaConcurrency>> downloadList;
    private List<Callable<UploadViaConcurrency>> uploadList;
    private ExecutorService executor;
    private final static Logger log = Logger.getLogger(ManagerViaConcurrency.class.getName());


    public ManagerViaConcurrency() {
        log.fine("Create new ManagerViaConcurrency");
        this.downloadList = new ArrayList<>();
        this.uploadList = new ArrayList<>();
        executor = Executors.newFixedThreadPool(20);

    }



    public void startDownload(String ... args) {
        createDownloads(args);
        log.fine("Start download");
        for (Callable<DownloadViaConcurrency> download : downloadList) {
            executor.submit(download);
        }
    }


    public void startUpload(String ... args) {
        createUploads(args);
        log.fine("Start upload");
        for (Callable<UploadViaConcurrency> upload : uploadList) {
            executor.submit(upload);
        }
    }


    public void endSession() {
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }
    }


    private void createDownloads(String ... args) {
        log.fine("Start create download");

        for (String fileName : args) {
            DownloadViaConcurrency d = new DownloadViaConcurrencyFromLocale(fileName);
            downloadList.add(d);
        }
    }


    private void createUploads(String ... args) {
        log.fine("Start create upload");
        for (int i = 0; i < args.length; i++) {
            UploadViaConcurrency d = new UploadViaConcurrencyToLocale(args[i]);
            uploadList.add(d);
        }
    }



    

}
