import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-04-30.
 */
public class CpuInfo {
    private ArrayList<String[]> cpuInfoArr; // 3G (none+hspa) LTE

    public CpuInfo() {
        this.cpuInfoArr = new ArrayList<>();
    }
    public void setPrevEndTime(String time){
        String[] prevInfo = cpuInfoArr.get(cpuInfoArr.size()-1);
        prevInfo[1] = time;
    }
    public String getPrevEndTime(){
        String[] prevInfo = cpuInfoArr.get((cpuInfoArr.size()-1));
        return prevInfo[1];
    }
    public String getPrevStartTime(){
        String[] prevInfo = cpuInfoArr.get((cpuInfoArr.size()-1));
        return prevInfo[0];
    }
    public int getInfoArrLength(){
        return cpuInfoArr.size();
    }
    public void setConnDuration(String duration) {
        String[] prevInfo = cpuInfoArr.get((cpuInfoArr.size()-1));
        prevInfo[2] = duration;
    }
    public void setCpuInfoArr(String[] cpuInfoArr){
        this.cpuInfoArr.add(cpuInfoArr);
    }
    public ArrayList<String[]> getCpuInfoArr() {
        return cpuInfoArr;
    }
}
