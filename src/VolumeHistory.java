/**
 * Created by Youlim Jung on 2017-05-18.
 */
public class VolumeHistory {
    private String summaryType;
    private int[] volumeLevel;

    public VolumeHistory() {
        this.summaryType = "";
        this.volumeLevel = new int[10];
    }

    public String getSummaryType() {
        return summaryType;
    }

    public void setSummaryType(String summaryType) {
        this.summaryType = summaryType;
    }

    public int[] getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(int level) {
        volumeLevel[level] = volumeLevel[level]+1;
    }
}
