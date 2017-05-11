import java.util.ArrayList;
import java.util.Date;
//import java.time.Duration;

/**
 * Created by Youlim Jung on 2017-04-29.
 */
public class ScreenInfo extends Information {
    private ArrayList<String> screenLevel;
    //private ArrayList<Date> screenDuration;
    private ArrayList<String> screenDuration;

    public ScreenInfo() {
        this.screenLevel = new ArrayList<>();
        this.screenDuration = new ArrayList<>();
    }

    public ArrayList<String> getScreenLevel() { return screenLevel; }

    public void setScreenLevel(String screenLevel) {
        this.screenLevel.add(screenLevel);
    }

    //public ArrayList<Date> getScreenDuration() { return screenDuration; }
    public ArrayList<String> getScreenDuration() { return screenDuration; }

//    public void setScreenDuration(Date screenDuration) { this.screenDuration.add(screenDuration); }
    public void setScreenDuration(String screenDuration) { this.screenDuration.add(screenDuration); }

}
