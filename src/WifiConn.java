import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-16.
 */
public class WifiConn extends Information{
    private ArrayList<String[]> wifiConnInfoArr;

    public WifiConn() {
        this.wifiConnInfoArr = new ArrayList<>();
    }
    // parsing
    @Override
    public void setPrevEndTime(String time){
        String[] prevInfo = wifiConnInfoArr.get(wifiConnInfoArr.size()-1);
        prevInfo[2] = time;
    }
    @Override
    public String getPrevEndTime(){
        String[] prevInfo = wifiConnInfoArr.get((wifiConnInfoArr.size()-1));
        return prevInfo[2];
    }
    @Override
    public String getPrevStartTime(){
        String[] prevInfo = wifiConnInfoArr.get((wifiConnInfoArr.size()-1));
        return prevInfo[1];
    }
    @Override
    public int getInfoArrLength(){
        return wifiConnInfoArr.size();
    }
    @Override
    public void setDuration(String duration) {
        String[] prevInfo = wifiConnInfoArr.get((wifiConnInfoArr.size()-1));
        prevInfo[3] = duration;
    }
    @Override
    public void addInfoArr(String[] infoArr){
        this.wifiConnInfoArr.add(infoArr);
    }
    // analyzing
    public ArrayList<String[]> getWifiConnInfoArr() {
        return wifiConnInfoArr;
    }
}
