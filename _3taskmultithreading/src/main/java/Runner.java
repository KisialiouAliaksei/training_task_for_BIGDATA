import action.Downloader;
import action.DownloaderWithShare;
import utils.Generator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by Aliaksei_Kisialiou on 7/19/2017.
 */
public class Runner {

    public static final String SERVER_DIR1 = "C:/test/server1/";
    public static final String SERVER_DIR2 = "C:/test/server2/";
    public static final String SERVER_DIR3 = "C:/test/server3/";
    public static final String SERVER_DIR4 = "C:/test/server4/";
    public static final String SERVER_DIR5 = "C:/test/server5/";


    public static final String DOWNLOAD_DIR1 = "C:/test/download1/";
    public static final String DOWNLOAD_DIR2 = "C:/test/download2/";
    public static final String DOWNLOAD_DIR3 = "C:/test/download3/";
    public static final String DOWNLOAD_DIR4 = "C:/test/download4/";
    public static final String DOWNLOAD_DIR5 = "C:/test/download5/";

    public static void main(String[] args) {
        long startAll = System.currentTimeMillis();
        Generator.cleanDir(SERVER_DIR1);
        Generator.cleanDir(SERVER_DIR2);
        Generator.cleanDir(SERVER_DIR3);
        Generator.generateFiles(SERVER_DIR1, 100);
        Generator.generateFiles(SERVER_DIR2, 100);
        Generator.generateFiles(SERVER_DIR3, 1000);


        Downloader downloader1 = new Downloader(SERVER_DIR1, DOWNLOAD_DIR1);
        Downloader downloader2 = new Downloader(SERVER_DIR2, DOWNLOAD_DIR2);
        Downloader downloader3 = new Downloader(SERVER_DIR3, DOWNLOAD_DIR3);


//        DownloaderWithShare downloader1 = new DownloaderWithShare(SERVER_DIR1, DOWNLOAD_DIR1);
//        DownloaderWithShare downloader2 = new DownloaderWithShare(SERVER_DIR2, DOWNLOAD_DIR2);
//        DownloaderWithShare downloader3 = new DownloaderWithShare(SERVER_DIR3, DOWNLOAD_DIR3);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        long start = System.currentTimeMillis();

        executor.submit(downloader1);
        executor.submit(downloader2);
        executor.submit(downloader3);

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }
        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        System.out.println("Work = " + timeConsumedMillis);
        System.out.println("generate and create = " + (start - startAll));


    }
}
