import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-03.
 */
public class UserLog {
    private String filename;
    private String totalTime;
    private String logStartTime;
    private String logEndTime;
    private AudioInfo audioInfo;
    private ConnInfo connInfo;
    private CpuInfo cpuInfo;
    private ScreenInfo screenInfo;
    private GpsInfo gpsInfo;

    public String getFilename() { return filename;}

    public void setFilename(String filename) { this.filename = filename; }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public void printTotalTime(){
        System.out.println("Total Usage Time: "+totalTime);
    }

    public String getLogStartTime() {
        return logStartTime;
    }

    public void setLogStartTime(String logStartTime) {
        this.logStartTime = logStartTime;
    }

    public String getLogEndTime() {
        return logEndTime;
    }

    public void setLogEndTime(String logEndTime) {
        this.logEndTime = logEndTime;
    }

    public AudioInfo getAudioInfo() {
        return audioInfo;
    }

    public void setAudioInfo(AudioInfo audioInfo) {
        this.audioInfo = audioInfo;
    }

    public ConnInfo getConnInfo() {
        return connInfo;
    }

    public void setConnInfo(ConnInfo connInfo) {
        this.connInfo = connInfo;
    }

    public CpuInfo getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(CpuInfo cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public ScreenInfo getScreenInfo() {
        return screenInfo;
    }

    public void setScreenInfo(ScreenInfo screenInfo) {
        this.screenInfo = screenInfo;
    }

    public GpsInfo getGpsInfo() { return gpsInfo; }

    public void setGpsInfo(GpsInfo gpsInfo) {
        this.gpsInfo = gpsInfo;
    }


}
