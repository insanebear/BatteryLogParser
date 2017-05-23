import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-16.
 */
public class BlueToothConn {
    private ArrayList<String[]> blueToothConnInfoArr;

    public BlueToothConn() {
        this.blueToothConnInfoArr = new ArrayList<>();
    }
    public void setPrevEndTime(String time){
        String[] prevInfo = blueToothConnInfoArr.get(blueToothConnInfoArr.size()-1);
        prevInfo[2] = time;
    }
    public String getPrevEndTime(){
        String[] prevInfo = blueToothConnInfoArr.get((blueToothConnInfoArr.size()-1));
        return prevInfo[2];
    }
    public String getPrevStartTime(){
        String[] prevInfo = blueToothConnInfoArr.get((blueToothConnInfoArr.size()-1));
        return prevInfo[1];
    }
    public int getInfoArrLength(){
        return blueToothConnInfoArr.size();
    }
    public void setConnDuration(String duration) {
        String[] prevInfo = blueToothConnInfoArr.get((blueToothConnInfoArr.size()-1));
        prevInfo[3] = duration;
    }
    public void setBlueToothConnInfoArr(String[] blueToothInfoArr){
        this.blueToothConnInfoArr.add(blueToothInfoArr);
    }
    public ArrayList<String[]> getBlueToothConnInfoArr() {
        return blueToothConnInfoArr;
    }
}
