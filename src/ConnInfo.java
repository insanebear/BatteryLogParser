//import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Youlim Jung on 2017-04-30.
 */
public class ConnInfo extends Information {
    private ArrayList<String> connType; // 3G (none+hspa), LTE, WiFi
    //private ArrayList<Date> connDuration;
    private ArrayList<String> connDuration;

    public ConnInfo() {
        this.connType = new ArrayList<>();
        this.connDuration = new ArrayList<>();
    }

    public ArrayList<String> getConnType() { return connType; }

    public void setConnType(String tyoe) {
        this.connType.add(tyoe);
    }

//    public ArrayList<Date> getConnDuration() { return connDuration; }
//
//    public void setConnDuration(ArrayList<Date> connDuration) {
//        this.connDuration = connDuration;
//    }

    public ArrayList<String> getConnDuration() { return connDuration; }

    public void setConnDuration(ArrayList<String> connDuration) {
        this.connDuration = connDuration;
    }

}
