import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-16.
 */
public class WifiConn {
    private ArrayList<String[]> wifiConnInfoArr; // 3G (none+hspa) LTE

    public WifiConn() {
        this.wifiConnInfoArr = new ArrayList<>();
    }
    public void setPrevEndTime(String time){
        String[] prevInfo = wifiConnInfoArr.get(wifiConnInfoArr.size()-1);
        prevInfo[1] = time;
    }
    public String getPrevEndTime(){
        String[] prevInfo = wifiConnInfoArr.get((wifiConnInfoArr.size()-1));
        return prevInfo[1];
    }
    public String getPrevStartTime(){
        String[] prevInfo = wifiConnInfoArr.get((wifiConnInfoArr.size()-1));
        return prevInfo[0];
    }
    public int getInfoArrLength(){
        return wifiConnInfoArr.size();
    }
    public void setConnDuration(String duration) {
        String[] prevInfo = wifiConnInfoArr.get((wifiConnInfoArr.size()-1));
        prevInfo[2] = duration;
    }
    public void setWifiConnInfoArr(String[] wifiCInfoArr){
        this.wifiConnInfoArr.add(wifiCInfoArr);
    }
    public ArrayList<String[]> getWifiConnInfoArr() {
        return wifiConnInfoArr;
    }
}
