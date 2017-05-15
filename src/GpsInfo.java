import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-03.
 */
public class GpsInfo extends Information {
    private ArrayList<String[]> gpsInfoArr; // start_timestamp, end_timestamp, duration

    public GpsInfo(){
        this.gpsInfoArr = new ArrayList<>();
    }
    public void setPrevEndTime(String time){
        String[] prevInfo = gpsInfoArr.get(gpsInfoArr.size()-1);
        prevInfo[1] = time; // set previous end_timestamp
    }
    public String getPrevEndTime(){
        String[] prevInfo = gpsInfoArr.get((gpsInfoArr.size()-1));
        return prevInfo[1];
    }
    public String getPrevStartTime(){
        String[] prevInfo = gpsInfoArr.get((gpsInfoArr.size()-1));
        return prevInfo[0];
    }
    public int getInfoArrLength(){
        return gpsInfoArr.size();
    }
    public void setGpsDuration(String duration) {
        String[] prevInfo = gpsInfoArr.get((gpsInfoArr.size()-1));
        prevInfo[2] = duration;
    }
    public void setGpsInfoArr(String[] gpsInfoArr){
        this.gpsInfoArr.add(gpsInfoArr);
    }
    public ArrayList<String[]> getGpsInfoArr() {
        return gpsInfoArr;
    }

    public void calTotalDuration(){
        String result="00h 00m 00s 00ms";
        for(String[] sa: gpsInfoArr){
            if(sa[2]!=null){
                result = addDuration(result,sa[2]);
            }
        }
        setGpsDuration(result);
    }

}
