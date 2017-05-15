import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-16.
 */
public class DataConn {
    private ArrayList<String[]> dataConnInfoArr; // 3G (none+hspa) LTE

    public DataConn() {
        this.dataConnInfoArr = new ArrayList<>();
    }
    public void setPrevEndTime(String time){
        String[] prevInfo = dataConnInfoArr.get(dataConnInfoArr.size()-1);
        prevInfo[2] = time;
    }
    public String getPrevEndTime(){
        String[] prevInfo = dataConnInfoArr.get((dataConnInfoArr.size()-1));
        return prevInfo[2];
    }
    public String getPrevStartTime(){
        String[] prevInfo = dataConnInfoArr.get((dataConnInfoArr.size()-1));
        return prevInfo[1];
    }
    public int getInfoArrLength(){
        return dataConnInfoArr.size();
    }
    public void setConnDuration(String duration) {
        String[] prevInfo = dataConnInfoArr.get((dataConnInfoArr.size()-1));
        prevInfo[3] = duration;
    }
    public void setDataConnInfoArr(String[] dataConnInfoArr){
        this.dataConnInfoArr.add(dataConnInfoArr);
    }
    public ArrayList<String[]> getDataConnInfoArr() {
        return dataConnInfoArr;
    }

}
