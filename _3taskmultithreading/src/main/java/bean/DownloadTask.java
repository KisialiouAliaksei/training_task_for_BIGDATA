package bean;

/**
 * Created by Aliaksei_Kisialiou on 7/20/2017.
 */
public class DownloadTask {
    private String source;
    private String dest;

    public DownloadTask(String source, String dest) {
        this.source = source;
        this.dest = dest;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "DownloadTask{" +
                "source='" + source + '\'' +
                ", dest='" + dest + '\'' +
                '}';
    }
}
