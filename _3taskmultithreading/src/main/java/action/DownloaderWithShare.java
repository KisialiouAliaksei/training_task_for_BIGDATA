package action;

import bean.DownloadTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Aliaksei_Kisialiou on 7/20/2017.
 */
public class DownloaderWithShare implements Callable<DownloaderWithShare> {
    private final String source;
    private final String dest;
    private static int size = 0 ;
    private Queue<DownloadTask> listForDownload;
    public static Lock lock = new ReentrantLock();
    private static AtomicInteger freeThreadsForDownloads = new AtomicInteger(0);
    private static AtomicInteger countDownloadTask = new AtomicInteger(0);
    public static BlockingQueue<DownloadTask> buffer = new ArrayBlockingQueue<>(10000);
    public static final int countShare = 2;

    public DownloaderWithShare(String source, String dest) {
        this.source = source;
        this.dest = dest;
        fillingQueue(source, dest);
    }

    @Override
    public DownloaderWithShare call() throws Exception {
        download();
        downloadHelp();

        return null;
    }

    public  void download() {
        while (!listForDownload.isEmpty()) {
            if (countDownloadTask.get() < size && freeThreadsForDownloads.get() > 0) {
                shareDownloadTasks();
            }
            if (!listForDownload.isEmpty()) {
                DownloadTask downloadTask = listForDownload.poll();
                try {
                    Files.move(Paths.get(downloadTask.getSource()), Paths.get(downloadTask.getDest()), REPLACE_EXISTING);
                    countDownloadTask.incrementAndGet();
                    waitDownload();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread = " + Thread.currentThread().getName() + ", File source = "
                        + downloadTask.getSource() + ", dest = " + downloadTask.getDest());
            }
        }
        freeThreadsForDownloads.incrementAndGet();
    }
    public void downloadHelp() {
        while (countDownloadTask.get() < size) {
            while (!buffer.isEmpty()) {
                DownloadTask downloadTask = buffer.poll();
                try {
                    Files.move(Paths.get(downloadTask.getSource()), Paths.get(downloadTask.getSource()), REPLACE_EXISTING);
                    countDownloadTask.incrementAndGet();

                    waitDownload();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("HELPER!!!Thread = " + Thread.currentThread().getName() + ", File source = "
                        + downloadTask.getSource() + ", dest = " + downloadTask.getDest());
            }
            System.out.println("HELPER!!!Thread = " + Thread.currentThread().getName() + "------------waiting!!!!!!!!");
        }
        freeThreadsForDownloads.decrementAndGet();

    }

    private void shareDownloadTasks() {
        for (int i = 0; i < countShare; i++) {
            DownloadTask downloadTask = listForDownload.poll();
            buffer.add(downloadTask);
        }
    }

    private void fillingQueue(String source, String dest) {
        File dir = new File(source);
        File[] list = dir.listFiles();
        size += list.length;
        listForDownload = new LinkedList<>();
        for (int i = 0; i < list.length; i++) {
            DownloadTask downloadTask = new DownloadTask(list[i].toString(), dest + list[i].getName());
            listForDownload.add(downloadTask);
        }
    }

    private void waitDownload() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
