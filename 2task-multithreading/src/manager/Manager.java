package manager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Aliaksei_Kisialiou on 7/18/2017.
 */
public interface Manager {
    void openSession();
    void startDownload(String ... args);
    void startDownloadAsynch(String ... args);
    void startUpload(String ... args);
    void startUploadAsynch(String ... args);
    void closeSession();



}
