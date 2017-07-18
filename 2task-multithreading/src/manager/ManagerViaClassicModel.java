package manager;

import action.classic_model.DownloadViaClassicModel;
import action.classic_model.DownloadViaClassicModelFromLocal;
import action.classic_model.UploadViaClassicModel;
import action.classic_model.UploadViaClassicModelToLocale;

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


    public ManagerViaClassicModel() {
        this.downloadList = new ArrayList<>();
        this.uploadList = new ArrayList<>();
    }

    public void startDownload(String ... args) {
        createDownloads(args);
        List<Thread> threads = new ArrayList<>();
        for (DownloadViaClassicModel download : downloadList) {
            Thread thread = new Thread(download);
            threads.add(thread);
            thread.start();
        }
        for ( Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void startUpload(String ... args) {
        createUploads(args);
        List<Thread> threads = new ArrayList<>();
        for (UploadViaClassicModel upload : uploadList) {
            Thread thread = new Thread(upload);
            threads.add(thread);
            thread.start();
        }
        for ( Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void endSession() {


    }


    private void createDownloads(String ... args) {
        for (String fileName : args) {
            DownloadViaClassicModel d = new DownloadViaClassicModelFromLocal(fileName);
            downloadList.add(d);
        }
    }


    private void createUploads(String ... args) {
        for (int i = 0; i < args.length; i++) {
            UploadViaClassicModel d = new UploadViaClassicModelToLocale(args[i]);
            uploadList.add(d);
        }
    }

}
