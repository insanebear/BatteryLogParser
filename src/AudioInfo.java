import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-04-30.
 */
/******* audioInfoArr *********
 *     [0]     [1]     [2]
 *   startT   endT   duration
 ******************************/
/************* VolumeHistory **************
 *     String               int[]
 *   summary type     volume count (0~15)
 * (fore/background)          ++
 *****************************************/
public class AudioInfo extends Information {
    private ArrayList<String[]> audioInfoArr;
    private VolumeHistory[] volumeHistories;
//    private ArrayList<VolumeHistory> volumeHistories;

    public AudioInfo() {
        this.audioInfoArr = new ArrayList<>();
        this.volumeHistories = new VolumeHistory[2];
        volumeHistories[0] = new VolumeHistory();
        volumeHistories[1] = new VolumeHistory();
    }
    @Override
    public void setPrevEndTime(String time){
        String[] prevInfo = audioInfoArr.get(audioInfoArr.size()-1);
        prevInfo[1] = time;
    }
    @Override
    public String getPrevEndTime(){
        String[] prevInfo = audioInfoArr.get((audioInfoArr.size()-1));
        return prevInfo[1];
    }
    @Override
    public String getPrevStartTime(){
        String[] prevInfo = audioInfoArr.get((audioInfoArr.size()-1));
        return prevInfo[0];
    }
    @Override
    public int getInfoArrLength(){
        return audioInfoArr.size();
    }
    @Override
    public void setDuration(String duration) {
        String[] prevInfo = audioInfoArr.get((audioInfoArr.size()-1));
        prevInfo[2] = duration;
    }
    @Override
    public void addInfoArr(String[] infoArr){
        this.audioInfoArr.add(infoArr);
    }

    public VolumeHistory[] getVolumeHistories() {
        return volumeHistories;
    }

    public ArrayList<String[]> getAudioInfoArr() {
        return audioInfoArr;
    }

    // check contents
    public void printAudioContents(){
        System.out.println("---------------- Audio Trace ----------------");
        printTrace(getAudioInfoArr());
        System.out.println();
        System.out.println("---------------- Volume Count ----------------");
        for(VolumeHistory volumeHistory: volumeHistories){
            System.out.println(volumeHistory.getSummaryType());
            for(int i=0; i<10; i++){
                System.out.print("[volume lev "+i+"] : ");
                System.out.println(volumeHistory.getVolumeLevel()[i]);
            }
        }
    }
}
