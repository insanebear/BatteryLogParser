import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-03.
 */
/******* gpsInfoArr *********
 *     [0]     [1]     [2]
 *   startT   endT   duration
 ****************************/
public class GpsInfo extends Information {
    private ArrayList<String[]> gpsInfoArr; // start, end, duration

    public GpsInfo(){
        this.gpsInfoArr = new ArrayList<>();
    }

    // parsing
    @Override
    public void setPrevEndTime(String time){
        String[] prevInfo = gpsInfoArr.get(gpsInfoArr.size()-1);
        prevInfo[1] = time; // set previous end_timestamp
    }
    @Override
    public String getPrevEndTime(){
        String[] prevInfo = gpsInfoArr.get((gpsInfoArr.size()-1));
        return prevInfo[1];
    }
    @Override
    public String getPrevStartTime(){
        String[] prevInfo = gpsInfoArr.get((gpsInfoArr.size()-1));
        return prevInfo[0];
    }
    @Override
    public int getInfoArrLength(){
        return gpsInfoArr.size();
    }
    @Override
    public void setDuration(String duration) {
        String[] prevInfo = gpsInfoArr.get((gpsInfoArr.size()-1));
        prevInfo[2] = duration;
    }
    @Override
    public void addInfoArr(String[] infoArr){
        this.gpsInfoArr.add(infoArr);
    }

    // analyzing
    public ArrayList<String[]> getGpsInfoArr() {
        return gpsInfoArr;
    }

    public void printGpsContents(){
        System.out.println("---------------- GPS Trace ----------------");
        printTrace(getGpsInfoArr());
    }

}
