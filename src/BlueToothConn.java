import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-16.
 */
public class BlueToothConn extends Information{
    private ArrayList<String[]> blueToothConnInfoArr;

    public BlueToothConn() {
        this.blueToothConnInfoArr = new ArrayList<>();
    }

    @Override
    public void setPrevEndTime(String time){
        String[] prevInfo = blueToothConnInfoArr.get(blueToothConnInfoArr.size()-1);
        prevInfo[2] = time;
    }

    @Override
    public String getPrevEndTime(){
        String[] prevInfo = blueToothConnInfoArr.get((blueToothConnInfoArr.size()-1));
        return prevInfo[2];
    }

    @Override
    public String getPrevStartTime(){
        String[] prevInfo = blueToothConnInfoArr.get((blueToothConnInfoArr.size()-1));
        return prevInfo[1];
    }

    @Override
    public int getInfoArrLength(){
        return blueToothConnInfoArr.size();
    }

    @Override
    public void setDuration(String duration) {
        String[] prevInfo = blueToothConnInfoArr.get((blueToothConnInfoArr.size()-1));
        prevInfo[3] = duration;
    }

    @Override
    public void addInfoArr(String[] infoArr){
        this.blueToothConnInfoArr.add(infoArr);
    }

    public void changeTimeFormat(String standard){
        if(blueToothConnInfoArr.size()>0){
            for(int i=0; i<blueToothConnInfoArr.size(); i++){
                //start
                blueToothConnInfoArr.get(i)[1] = subtractTimes(blueToothConnInfoArr.get(i)[1], standard);
                //end
                blueToothConnInfoArr.get(i)[2] = subtractTimes(blueToothConnInfoArr.get(i)[2], standard);
            }
        }
    }

    // analyzing
    public ArrayList<String[]> getBlueToothConnInfoArr() {
        return blueToothConnInfoArr;
    }
}
