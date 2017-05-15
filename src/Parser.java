import java.io.*;
import java.text.ParseException;

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
//            if (s.matches("  Screen brightnesses:")) {
////                for (int i = 0; i < 5; i++) {
////                    reader.readLine();
////                    s = reader.readLine();
////                    parseScreen(s);
////                }
//
////            }else if (s.matches("  Radio types:")) {
////                while(!(s = reader.readLine()).contains("Bluetooth")){
////                    parseDataConn(s);
////                }
////                userLog.setConnInfo(connInfo);
//            }else
            if (s.contains("Battery History")){
                while(!(s = reader.readLine()).contains("Per-PID Stats:")){
                    if(s.contains("gps")){
                        parseGps(s);
                    }
                    if(s.contains("brightness=") || s.contains("screen ")){
                        parseScreen(s);
                    }
                    if(s.contains("mobile_radio") || s.contains("data_conn=")){
                        parseDataConn(s);
                    }
                    if(s.contains("wifi_radio")){
                        parseWifiConn(s);
                    }
                }

                // GPS
                gpsInfo.calTotalDuration();
                userLog.setGpsInfo(gpsInfo);
                // SCREEN
                userLog.setScreenInfo(screenInfo);
            }
        }

        reader.close();
        return userLog;
    }

    private void parseScreen(String s){
        final String BRIGHTNESS_CHECK = "brightness=";
        String[] tempInfo = new String[4]; // level, start, end, duration
        String line = s.trim();

        String time, startT, endT, duration;

        int brightIdx = line.indexOf(BRIGHTNESS_CHECK)+BRIGHTNESS_CHECK.length();

        // Parsing time
        if(line.charAt(0)=='0'){
            time = "0h 0m 0s 0ms";
        }else{
            time = insertSpace(line.substring(1, line.indexOf("(")-1));
        }
        time = screenInfo.padZero(makeTimeArray(time));
        tempInfo[1] = time;

        // Parsing information
        if(line.contains("screen ") && !line.contains(BRIGHTNESS_CHECK)){
            int idx = line.indexOf("screen ");
            if(line.charAt(idx-1)=='+'){
                // Assume brightness as MEDIUM
                tempInfo[0] = "medium";
            }else{
                // Assume brightness as DARK
                tempInfo[0] = "dark";
            }
        }else{
            if(line.charAt(brightIdx) == 'd'){
                if(line.charAt(brightIdx+1)== 'i'){ // dim
                    tempInfo[0] = "dim";
                }else{ // dark
                    tempInfo[0] = "dark";
                }
            }else if(line.charAt(brightIdx) == 'm'){ // medium
                tempInfo[0] = "medium";
            }else if(line.charAt(brightIdx) == 'l'){ // light
                tempInfo[0] = "light";
            }else{ // bright
                tempInfo[0] = "bright";
            }
        }

        if(screenInfo.getInfoArrLength()>0){
            screenInfo.setPrevEndTime(time);

            startT = screenInfo.getPrevStartTime();
            endT = screenInfo.getPrevEndTime();
            duration = screenInfo.calculateDuration(endT, startT);
            screenInfo.setScreenDuration(duration);
        }
        screenInfo.setScreenInfoArr(tempInfo);
    }

    private void parseGps(String s) throws ParseException{
        String line = s.trim();
        String time, startT, endT, duration;
        String[] tempInfo = new String[3]; // start, end, duration
        int idx = line.indexOf("gps");

        // Parsing time
        if(line.charAt(0)=='0'){
            time = "0h 0m 0s 0ms";
        }else{
            time = insertSpace(line.substring(1, line.indexOf("(")-1));
        }
        time = gpsInfo.padZero(makeTimeArray(time));
        tempInfo[0] = time;

        if(gpsInfo.getInfoArrLength()>0){
            gpsInfo.setPrevEndTime(time);

            // calculate previous duration
            startT = gpsInfo.getPrevStartTime();
            endT = gpsInfo.getPrevEndTime();
            duration = gpsInfo.calculateDuration(endT, startT);
            gpsInfo.setGpsDuration(duration);
        }
        gpsInfo.setGpsInfoArr(tempInfo);

    }

    private void parseDataConn(String s){
        final String DATACONN_CHECK = "data_conn=";
        String line = s.trim();
        String[] tempInfo = new String[4]; // type, start, end, duration

        String time, startT, endT, duration;

        // Parsing time
        if(line.charAt(0)=='0'){
            time = "0h 0m 0s 0ms";
        }else{
            time = insertSpace(line.substring(1, line.indexOf("(")-1));
        }
        time = connInfo.padZero(makeTimeArray(time));
        tempInfo[1] = time;

        // Parsing information
        if(line.contains("+mobile_radio") && !line.contains(DATACONN_CHECK)){
            // Assume conn_type = "3g"
            tempInfo[0] = "3g";
        }else if(line.contains(DATACONN_CHECK)) {
            int idx = line.indexOf(DATACONN_CHECK) + DATACONN_CHECK.length();
            if (line.charAt(idx) == 'n' || line.charAt(idx) == 'h') { // none or hspa
                tempInfo[0] = "3g";
            } else if (line.charAt(idx) == 'l') { // lte
                tempInfo[0] = "lte";
            }
        }

        if(connInfo.getDataConn().getInfoArrLength() > 0){
            connInfo.getDataConn().setPrevEndTime(time);

            startT = connInfo.getDataConn().getPrevStartTime();
            endT = connInfo.getDataConn().getPrevEndTime();
            duration = connInfo.calculateDuration(endT, startT);
            connInfo.getDataConn().setConnDuration(duration);
        }
        connInfo.getDataConn().setDataConnInfoArr(tempInfo);

    }

    private void parseWifiConn(String s){
        final String WIFI_CHECK = "wifi_radio";
        String line = s.trim();
        String[] tempInfo = new String[3]; // start, end, duration

        String time, startT, endT, duration;

        // Parsing time
        if(line.charAt(0)=='0'){
            time = "0h 0m 0s 0ms";
        }else{
            time = insertSpace(line.substring(1, line.indexOf("(")-1));
        }
        time = connInfo.padZero(makeTimeArray(time));

        int idx = line.indexOf(WIFI_CHECK);
        if(line.charAt(idx-1)=='+'){
            tempInfo[0] = time;
        }else{
            if(connInfo.getWifiConn().getInfoArrLength() > 0){
                connInfo.getWifiConn().setPrevEndTime(time);

                startT = connInfo.getWifiConn().getPrevStartTime();
                endT = connInfo.getWifiConn().getPrevEndTime();
                duration = connInfo.calculateDuration(endT, startT);
                connInfo.getWifiConn().setConnDuration(duration);
            }
        }
        connInfo.getWifiConn().setWifiConnInfoArr(tempInfo);
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

//        System.out.println("Array: "+Arrays.toString(timeArray));
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


//    private String arrToString(String[] strings){
//        String result = "";
////        for(int i=0; i<strings.length; i++){
////            result = result + strings[i];
////        }
//        for (String s : strings){
//            result+=s;
//        }
//        return insertSpace(result);
//    }
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

//    private void parseScreen(String s) throws ParseException {
//        String line = s.trim();
//        String[] values = line.split(" ");
//        String[] subvalues = Arrays.copyOfRange(values, 1, values.length-1);
//        String time = arrToString(subvalues);
//        String[] timeArray = makeTimeArray(time);
//
//        screenInfo.setScreenInfoArr(values[0]);
//
//        screenInfo.setScreenDuration(screenInfo.padZero(timeArray));
//
//    }
}

