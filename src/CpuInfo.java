import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-04-30.
 */
/******* cpuInfoArr *********
 *     [0]     [1]     [2]
 *   startT   endT   duration
 ****************************/

public class CpuInfo extends Information{
    private ArrayList<String[]> cpuInfoArr;


    public CpuInfo() {
        this.cpuInfoArr = new ArrayList<>();
    }

    // parsing
    @Override
    public void setPrevEndTime(String time){
        String[] prevInfo = cpuInfoArr.get(cpuInfoArr.size()-1);
        prevInfo[1] = time;
    }

    @Override
    public String getPrevEndTime(){
        String[] prevInfo = cpuInfoArr.get((cpuInfoArr.size()-1));
        return prevInfo[1];
    }

    @Override
    public String getPrevStartTime(){
        String[] prevInfo = cpuInfoArr.get((cpuInfoArr.size()-1));
        return prevInfo[0];
    }

    @Override
    public int getInfoArrLength(){
        return cpuInfoArr.size();
    }

    @Override
    public void setDuration(String duration) {
        String[] prevInfo = cpuInfoArr.get((cpuInfoArr.size()-1));
        prevInfo[2] = duration;
    }

    public void addInfoArr(String[] infoArr){
        this.cpuInfoArr.add(infoArr);
    }
    // analyzing
    public ArrayList<String[]> getCpuInfoArr() {
        return cpuInfoArr;
    }

    // checking
    public void printCpuContents(){
        System.out.println("---------------- CPU Trace ----------------");
        printTrace(getCpuInfoArr());
    }
}
