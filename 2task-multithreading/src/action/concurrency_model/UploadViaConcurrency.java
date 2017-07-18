package action.concurrency_model;

import java.util.concurrent.Callable;

/**
 * Created by Aliaksei_Kisialiou on 7/18/2017.
 */
public interface UploadViaConcurrency extends Callable<UploadViaConcurrency> {
    void upload(String file);
}
