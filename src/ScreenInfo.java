import java.util.ArrayList;
//import java.time.Duration;

/**
 * Created by Youlim Jung on 2017-04-29.
 */
public class ScreenInfo extends Information {
    private ArrayList<String[]> screenInfoArr; // level(brightness), start_timestamp, end_timestamp, duration

    public ScreenInfo() {
        this.screenInfoArr = new ArrayList<>();
    }

    public void setPrevEndTime(String time){
        String[] prevInfo = screenInfoArr.get(screenInfoArr.size()-1);
        prevInfo[2] = time; // set previous end_timestamp
    }
    public String getPrevEndTime(){
        String[] prevInfo = screenInfoArr.get((screenInfoArr.size()-1));
        return prevInfo[2];
    }
    public String getPrevStartTime(){
        String[] prevInfo = screenInfoArr.get((screenInfoArr.size()-1));
        return prevInfo[1];
    }

    public int getInfoArrLength(){
        return screenInfoArr.size();
    }


    public void setScreenDuration(String duration){
        String[] prevInfo = screenInfoArr.get((screenInfoArr.size()-1));
        prevInfo[3] = duration;
    }
    public String getPrevScreenLevel(){
        return screenInfoArr.get((screenInfoArr.size()-1))[0];
    }

    public void setScreenInfoArr(String[] screenInfoArr){
        this.screenInfoArr.add(screenInfoArr);
    }

    public ArrayList<String[]> getScreenInfoArr() {
        return screenInfoArr;
    }

}
