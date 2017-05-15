import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-03.
 */
public class UserLog {
    private String filename;
    private AudioInfo audioInfo;
    private ConnInfo connInfo;
    private CpuInfo cpuInfo;
    private ScreenInfo screenInfo;
    private GpsInfo gpsInfo;

    public String getFilename() { return filename;}

    public void setFilename(String filename) { this.filename = filename; }

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

    public void getGpsInfo() {
//        System.out.println("GPS Total Duration:"+gpsInfo.getGpsDuration());
    }

    public void setGpsInfo(GpsInfo gpsInfo) {
        this.gpsInfo = gpsInfo;
    }



    public void getScreenContents(){
        ArrayList<String[]> screenHistory = screenInfo.getScreenInfoArr();
        int idx = 1;
        for (String[] sa :
                screenHistory) {
            System.out.println(idx+"th trace: [Level] "+sa[0]+" [Start Time] "+sa[1]
                    +" [End Time] "+sa[2]+" [Duration] "+sa[3]);
            idx++;
        }
    }

    public void getGpsContents(){
        ArrayList<String[]> gpsHistory = gpsInfo.getGpsInfoArr();
        int idx = 1;
        for (String[] sa :
                gpsHistory) {
            System.out.println(idx+"th trace: [Start Time] "+sa[0]
                    +" [End Time] "+sa[1]+" [Duration] "+sa[2]);
            idx++;
        }
    }
}
