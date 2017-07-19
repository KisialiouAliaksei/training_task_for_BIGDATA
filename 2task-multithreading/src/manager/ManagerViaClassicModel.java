package manager;

import action.classic_model.DownloadViaClassicModel;
import action.classic_model.DownloaderViaClassicModelFromLocal;
import action.classic_model.UploadViaClassicModel;
import action.classic_model.UploaderViaClassicModelToLocale;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Aliaksei_Kisialiou on 7/17/2017.
 */
public class ManagerViaClassicModel implements Manager{
    private List<DownloadViaClassicModel> downloadList;
    private List<UploadViaClassicModel> uploadList;
    private final static Logger log = Logger.getLogger(ManagerViaClassicModel.class.getName());
    public volatile static int countFinishedUploads = 0;
    public volatile static int countFinishedDownload = 0;

    public ManagerViaClassicModel() {
        this.downloadList = new ArrayList<>();
        this.uploadList = new ArrayList<>();
    }

    @Override
    public void openSession() {

    }

    public void startDownload(String ... args) {
        createDownloads(args);
        for (DownloadViaClassicModel download : downloadList) {
            Thread thread = new Thread(download);
            thread.start();
        }
        while (countFinishedDownload < downloadList.size()) {

        }
        downloadList.clear();
    }
    public void startDownloadAsynch(String ... args) {
        createDownloads(args);
        for (DownloadViaClassicModel download : downloadList) {
            Thread thread = new Thread(download);
            thread.start();
        }
        downloadList.clear();
    }
    

    public void startUpload(String ... args) {
        createUploads(args);
        for (UploadViaClassicModel upload : uploadList) {
            Thread thread = new Thread(upload);
            thread.start();
        }
        while (countFinishedUploads < uploadList.size()) {

        }
        uploadList.clear();
    }
    public void startUploadAsynch(String ... args) {
        createUploads(args);
        for (UploadViaClassicModel upload : uploadList) {
            Thread thread = new Thread(upload);
            thread.start();
        }
        uploadList.clear();
    }



    public void closeSession() {

    }

    private void createDownloads(String ... args) {
        if (args.length == 0) {
            return;
        }
        for (String fileName : args) {
            DownloadViaClassicModel d = new DownloaderViaClassicModelFromLocal(fileName);
            downloadList.add(d);
        }
    }


    private void createUploads(String ... args) {
        if (args.length == 0) {
            return;
        }
        for (int i = 0; i < args.length; i++) {
            UploadViaClassicModel d = new UploaderViaClassicModelToLocale(args[i]);
            uploadList.add(d);
        }
    }

}
