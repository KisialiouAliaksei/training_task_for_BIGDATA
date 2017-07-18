package manager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Aliaksei_Kisialiou on 7/18/2017.
 */
public interface Manager {
    void startDownload(String ... args);
    void startUpload(String ... args);
    void endSession();



}
