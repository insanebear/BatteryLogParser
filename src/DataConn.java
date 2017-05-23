import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-16.
 */
/******* dataConnInfoArr *********
 *     [0]     [1]     [2]     [3]
 *     Type  startT   endT   duration
 ****************************/
public class DataConn extends Information {
    private ArrayList<String[]> dataConnInfoArr; // 3G (none+hspa) LTE

    public DataConn() {
        this.dataConnInfoArr = new ArrayList<>();
    }
    @Override
    public void setPrevEndTime(String time){
        String[] prevInfo = dataConnInfoArr.get(dataConnInfoArr.size()-1);
        prevInfo[2] = time;
    }
    @Override
    public String getPrevEndTime(){
        String[] prevInfo = dataConnInfoArr.get((dataConnInfoArr.size()-1));
        return prevInfo[2];
    }
    @Override
    public String getPrevStartTime(){
        String[] prevInfo = dataConnInfoArr.get((dataConnInfoArr.size()-1));
        return prevInfo[1];
    }
    @Override
    public int getInfoArrLength(){
        return dataConnInfoArr.size();
    }
    @Override
    public void setDuration(String duration) {
        String[] prevInfo = dataConnInfoArr.get((dataConnInfoArr.size()-1));
        prevInfo[3] = duration;
    }
    public void addInfoArr(String[] infoArr){
        this.dataConnInfoArr.add(infoArr);
    }
    // analyzing
    public ArrayList<String[]> getDataConnInfoArr() {
        return dataConnInfoArr;
    }

}
