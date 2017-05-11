import java.io.*;
import java.text.ParseException;
import java.util.Arrays;

/**
 * Created by Youlim Jung on 2017-05-02.
 */
public class Parser {
    private File file;
    private AudioInfo audioInfo;
    private ConnInfo connInfo;
    private CpuInfo cpuInfo;
    private ScreenInfo screenInfo;
    private GpsInfo gpsInfo;
    private UserLog userLog;

    public Parser(File file) {
        this.file = file;
        audioInfo = new AudioInfo();
        connInfo = new ConnInfo();
        cpuInfo = new CpuInfo();
        screenInfo = new ScreenInfo();
        gpsInfo = new GpsInfo();
        userLog = new UserLog();
    }

    public UserLog parseLog() throws IOException, ParseException {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String s;

        System.out.println("Now parsing "+file.getName());
        userLog.setFilename(file.getName());

        while ((s = reader.readLine()) != null) {
            if (s.matches("  Screen brightnesses:")) {
                for (int i = 0; i < 5; i++) {
                    reader.readLine();
                    s = reader.readLine();
                    parseScreen(s);
                }
                userLog.setScreenInfo(screenInfo);
            }else if (s.matches("  Radio types:")) {
                while(!(s = reader.readLine()).contains("Bluetooth")){
                    parseConn(s);
                }
                userLog.setConnInfo(connInfo);
            }else if (s.contains("Battery History")){
                while(!(s = reader.readLine()).contains("Per-PID Stats:")){
                    if(s.contains("gps")){
                        parseGps(s);
                    }
                }
                //gpsInfo.printDurationList();
                gpsInfo.calTotalDuration();
                userLog.setGpsInfo(gpsInfo);
            }
        }

        reader.close();
        return userLog;
    }

    private void parseScreen(String s) throws ParseException {
        String line = s.trim();
        String[] values = line.split(" ");
        String[] subvalues = Arrays.copyOfRange(values, 1, values.length-1);
        String time = arrToString(subvalues);
        String[] timeArray = makeTimeArray(time);

        screenInfo.setScreenLevel(values[0]);

        screenInfo.setScreenDuration(screenInfo.padZero(timeArray));

    }

    private void parseGps(String s) throws ParseException{
        String line = s.trim();
        String[] values = line.split(" ");
        String time = insertSpace(values[0].substring(1));
        String[] timeArray = makeTimeArray(time); // h, min, sec, msec
        int idx = s.indexOf("gps");

        if(s.charAt(idx-1) == '+'){
            //set start stamp
            //System.out.println("in parseGps: "+padZero(timeArray));
            gpsInfo.setStartStamp(gpsInfo.padZero(timeArray));
        }else{
            //set end stamp
            gpsInfo.setEndStamp(gpsInfo.padZero(timeArray));
            //calculate duration time
            String duration = gpsInfo.calculateDuration(gpsInfo.getEndStamp(), gpsInfo.getStartStamp());
            gpsInfo.appendDurationList(duration);
        }
    }

    private void parseConn(String s){
        String line = s.trim();
        String[] values = line.split(" ");
        String[] subvalues = Arrays.copyOfRange(values, 1, values.length-1);
        String time = arrToString(subvalues);
        String[] timeArray = makeTimeArray(time);

        connInfo.setConnType(values[0]);

        //screenInfo.setScreenDuration(screenInfo.padZero(timeArray));
        if(values[0].matches("none") || values[0].matches("hspa")){

        }else if(values[0].matches("lte")){

        }
        // 3G

        // LTE

        // WiFi
    }

    private String[] makeTimeArray(String timeString) {
        String[] timeArray = new String[4];
        String[] tempArray = timeString.split(" ");

        for (String element : tempArray) {
            String sub = element.substring(element.length() - 2);
            if (sub.matches("ms")) {
                timeArray[3] = element.substring(0, element.length() - 2);
            } else {
                if (element.substring(element.length() - 1).matches("m")) {
                    timeArray[1] = element.substring(0, element.length() - 1);
                } else if (element.substring(element.length() - 1).matches("s")) {
                    timeArray[2] = element.substring(0, element.length() - 1);
                } else if (element.substring(element.length() - 1).matches("h")) {
                    timeArray[0] = element.substring(0, element.length() - 1);
                }
            }
        }

        //System.out.println("Array: "+Arrays.toString(timeArray));
        return timeArray;
    }

    private String insertSpace(String original){
        int idx;
        String result="";
        //System.out.println("before space:" + original);
        // h
        idx = original.indexOf('h');
        if(idx != -1){
            result = result + original.substring(0, idx+1) + " ";
            original = original.substring(idx+1);
        }

        // m
        idx = original.indexOf('m');
        if(idx!= -1 && original.charAt(idx+1) != 's'){
            result = result + original.substring(0, idx+1) + " ";
            original = original.substring(idx+1);
        }

        // s
        idx = original.indexOf('s');
        if(idx!= -1 && original.charAt(idx-1) != 'm'){
            result = result + original.substring(0, idx+1)+" ";
            original = original.substring(idx+1);
        }

        // ms
        idx = original.indexOf('m');
        if(idx!= -1 && original.charAt(idx+1) == 's'){
            result += original;
        }
        //System.out.println("after space:" + result);
        return result;
    }

    private String arrToString(String[] strings){
        String result = "";
//        for(int i=0; i<strings.length; i++){
//            result = result + strings[i];
//        }
        for (String s : strings){
            result+=s;
        }
        return insertSpace(result);
    }
//    private String padZero(String[] strings){
//        for (int i=0; i<strings.length; i++) {
//            if(strings[i]==null && i != 3){ // no digit h, m, s
//                strings[i] = "00";
//            }else if(strings[i].length()==1 && i != 3){ // 1 digit h, m, s
//                strings[i] = "0"+strings[i];
//            }else if(i == 3){ // ms
//                if(strings[i].length() == 1){
//                    strings[i] = "00"+strings[i];
//                }else if (strings[i].length() == 2){
//                    strings[i] = "0"+strings[i];
//                }else if(strings[i]==null){
//                    strings[i] = "000";
//                }
//            }
//        }
//        return addUnits(strings);
//    }
//
//    private String addUnits(String[] values){
//        return values[0]+"h "+values[1]+"m "+values[2]+"s "+values[3]+"ms";
//    }

//    private String[] formatTimeStr(String[] values){
//        String[] result = new String[4];
//        for(String value: values){
//            String sub =value.substring(value.length()-2);
//            if(sub.matches("ms")){
//                result[3] = value.substring(0, value.length()-2);
//            }else{
//                if(value.substring(value.length()-1).matches("m")) {
//                    result[1] = value.substring(0, value.length()-1);
//                }else if(value.substring(value.length()-1).matches("s")){
//                    result[2] = value.substring(0, value.length()-1);
//                }else if(value.substring(value.length()-1).matches("h")){
//                    result[0] = value.substring(0, value.length()-1);
//                }
//            }
//        }
//
//        return result;
//    }

//    private String convertFormat(String original){
//        String[] res = original.split("[a-z]");
//        //return padZero(formatTimeStr(res));
//        return padZero(res);
//    }
}
