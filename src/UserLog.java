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
        System.out.println("GPS Total Duration:"+gpsInfo.getGpsDuration());
    }

    public void setGpsInfo(GpsInfo gpsInfo) {
        this.gpsInfo = gpsInfo;
    }



    public void getScreenContents(){
        for(int i=0; i<screenInfo.getScreenLevel().size();i++){
            System.out.println(screenInfo.getScreenLevel().get(i));
            System.out.println(screenInfo.getScreenDuration().get(i));
            //System.out.println(screenInfo.dateToStr(screenInfo.getScreenDuration().get(i)));
        }
    }
}
