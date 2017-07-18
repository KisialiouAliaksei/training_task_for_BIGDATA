package manager;

import action.classic_model.DownloadViaClassicModel;
import action.classic_model.DownloadViaClassicModelFromLocal;
import action.classic_model.UploadViaClassicModel;
import action.classic_model.UploadViaClassicModelToLocale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aliaksei_Kisialiou on 7/17/2017.
 */
public class ManagerViaClassicModel implements Manager{
    private List<DownloadViaClassicModel> downloadList;
    private List<UploadViaClassicModel> uploadList;

    public ManagerViaClassicModel() {
        this.downloadList = new ArrayList<>();
        this.uploadList = new ArrayList<>();
    }

    public void startDownload(String ... args) {
        createDownloads(args);
        for (DownloadViaClassicModel download : downloadList) {
            Thread thread = new Thread(download);
            thread.start();
        }
    }


    public void startUpload(String ... args) {
        createUploads(args);
        for (UploadViaClassicModel upload : uploadList) {
            Thread thread = new Thread(upload);
            thread.start();
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
