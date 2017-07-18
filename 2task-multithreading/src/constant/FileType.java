package constant;

/**
 * Created by Aliaksei_Kisialiou on 7/18/2017.
 */
public enum FileType {
    TXT,
    MP3;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
