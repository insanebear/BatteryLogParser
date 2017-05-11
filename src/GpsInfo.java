import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-03.
 */
public class GpsInfo extends Information {
    private String startStamp;
    private String endStamp;
    private String gpsDuration;
    private ArrayList<String> durationList;

    public GpsInfo(){
        durationList = new ArrayList<>();
    }

    public String getStartStamp() {
        return startStamp;
    }

    public void setStartStamp(String startStamp) {
        this.startStamp = startStamp;
    }

    public String getEndStamp() {
        return endStamp;
    }

    public void setEndStamp(String endStamp) {
        this.endStamp = endStamp;
    }

    public String getGpsDuration() {
        return gpsDuration;
    }

    private void setGpsDuration(String gpsDuration) {
        this.gpsDuration = gpsDuration;
    }

    public void appendDurationList(String duration){
        this.durationList.add(duration);
    }

    public void printDurationList(){
        for(String s:durationList){
            System.out.println(s);
        }
    }

    public void calTotalDuration(){
        String result="00h 00m 00s 00ms";
        for(String s: durationList){
            result = addDuration(result,s);
        }
        setGpsDuration(result);
    }

    //    private Date startStamp;
//    private Date endStamp;
//    private Date gpsDuration;
//
//    public GpsInfo(){
//
//    }
//
//    public Date getStartStamp() {
//        return startStamp;
//    }
//
//    public void setStartStamp(Date startStamp) {
//        this.startStamp = startStamp;
//    }
//
//    public Date getEndStamp() {
//        return endStamp;
//    }
//
//    public void setEndStamp(Date endStamp) {
//        this.endStamp = endStamp;
//    }
//
//    public Date getGpsDuration() {
//        return gpsDuration;
//    }
//
//    public void setGpsDuration(Date gpsDuration) {
//        this.gpsDuration = gpsDuration;
//    }

}
