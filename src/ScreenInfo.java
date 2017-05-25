import java.util.ArrayList;
//import java.time.Duration;

/**
 * Created by Youlim Jung on 2017-04-29.
 */
/********** screenInfoArr ************
 *     [0]     [1]     [2]     [3]
 *    level  startT   endT   duration
 *************************************/
public class ScreenInfo extends Information {
    private ArrayList<String[]> screenInfoArr; // level(brightness), start_timestamp, end_timestamp, duration

    public ScreenInfo() {
        this.screenInfoArr = new ArrayList<>();
    }

    // parsing
    @Override
    public void setPrevEndTime(String time){
        String[] prevInfo = screenInfoArr.get(screenInfoArr.size()-1);
        prevInfo[2] = time; // set previous end_timestamp
    }

    @Override
    public String getPrevEndTime(){
        String[] prevInfo = screenInfoArr.get((screenInfoArr.size()-1));
        return prevInfo[2];
    }

    @Override
    public String getPrevStartTime(){
        String[] prevInfo = screenInfoArr.get((screenInfoArr.size()-1));
        return prevInfo[1];
    }

    @Override
    public int getInfoArrLength(){
        return screenInfoArr.size();
    }

    @Override
    public void setDuration(String duration){
        String[] prevInfo = screenInfoArr.get((screenInfoArr.size()-1));
        prevInfo[3] = duration;
    }

    @Override
    public void addInfoArr(String[] infoArr){
        this.screenInfoArr.add(infoArr);
    }

    // analyzing
    public ArrayList<String[]> getScreenInfoArr() {
        return screenInfoArr;
    }

    // check contents
    public void printScreenContents(){
        ArrayList<String[]> traceBright = findBrightTrace();
        ArrayList<String[]> traceLight = findLightTrace();
        ArrayList<String[]> traceMedium = findMediumTrace();
        ArrayList<String[]> traceDim = findDimTrace();
        ArrayList<String[]> traceDark = findDarkTrace();

        System.out.println("---------------- Screen Trace ----------------");
        System.out.println("<<<<<<<<<<<<< Bright Trace >>>>>>>>>>>>");
        printNamedTrace(traceBright);
        System.out.println("<<<<<<<<<<<<< Light Trace >>>>>>>>>>>");
        printNamedTrace(traceLight);
        System.out.println("<<<<<<<<<<<<< Medium Trace >>>>>>>>>>>");
        printNamedTrace(traceMedium);
        System.out.println("<<<<<<<<<<<<< Dim Trace >>>>>>>>>>>");
        printNamedTrace(traceDim);
        System.out.println("<<<<<<<<<<<<< Dark Trace >>>>>>>>>>>");
        printNamedTrace(traceDark);
    }

    public ArrayList<String[]> findBrightTrace(){
        ArrayList<String[]> traceBright = new ArrayList<>();

        if(getInfoArrLength() > 0){
            for (int i=0; i<getInfoArrLength(); i++) {
                String[] trace = screenInfoArr.get(i);
                if (trace[0].equals("bright")) {
                    traceBright.add(trace);
                }
            }
        }
        return traceBright;
    }

    public ArrayList<String[]> findLightTrace(){
        ArrayList<String[]> traceLight = new ArrayList<>();

        if(getInfoArrLength() > 0){
            for (int i=0; i<getInfoArrLength(); i++) {
                String[] trace = screenInfoArr.get(i);
                if (trace[0].equals("light")) {
                    traceLight.add(trace);
                }
            }
        }
        return traceLight;
    }

    public ArrayList<String[]> findMediumTrace(){
        ArrayList<String[]> traceMedium = new ArrayList<>();

        if(getInfoArrLength() > 0){
            for (int i=0; i<getInfoArrLength(); i++) {
                String[] trace = screenInfoArr.get(i);
                if (trace[0].equals("medium")) {
                    traceMedium.add(trace);
                }
            }
        }
        return traceMedium;
    }

    public ArrayList<String[]> findDimTrace(){
        ArrayList<String[]> traceDim = new ArrayList<>();

        if(getInfoArrLength() > 0){
            for (int i=0; i<getInfoArrLength(); i++) {
                String[] trace = screenInfoArr.get(i);
                if (trace[0].equals("dim")) {
                    traceDim.add(trace);
                }
            }
        }
        return traceDim;
    }

    public ArrayList<String[]> findDarkTrace(){
        ArrayList<String[]> traceDark = new ArrayList<>();

        if(getInfoArrLength() > 0){
            for (int i=0; i<getInfoArrLength(); i++) {
                String[] trace = screenInfoArr.get(i);
                if (trace[0].equals("dark")) {
                    traceDark.add(trace);
                }
            }
        }
        return traceDark;
    }

}
