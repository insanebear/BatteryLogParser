import java.io.*;
import java.text.ParseException;

/**
 * Created by Youlim Jung on 2017-05-02.
 */
public class Parser {
    private File file;
    private Information info;
    private AudioInfo audioInfo;
    private ConnInfo connInfo;
    private CpuInfo cpuInfo;
    private ScreenInfo screenInfo;
    private GpsInfo gpsInfo;
    private UserLog userLog;

    public Parser(File file) {
        this.file = file;
        info = new Information();
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
                    if(s.contains("+running") || s.contains("-running")){
                        parseCpu(s);
                    }
                    if(s.contains("+audio") || s.contains("-audio")){
                        parseAudio(s);
                    }
                }
            } else if(s.contains("Historical broadcasts summary [foreground]:")){
                while(!s.contains("Historical broadcasts [background]:") &&
                        !s.contains("Delayed Historical broadcasts [foreground]:")){
                    parseVolumeFore(s);
                    s = reader.readLine();
                }
            }else if(s.contains("Historical broadcasts summary [background]:")){
                while(!s.contains("Sticky broadcasts for user -1:") &&
                        !s.contains("Aborted Historical broadcasts [background]:")){

                    parseVolumeBack(s);
                    s = reader.readLine();
                }
            }else if(s.contains("Total run time:")){
                parseTimeOnBattery(s);
            }
        }

        // CPU
        userLog.setCpuInfo(cpuInfo);
        // Audio
        userLog.setAudioInfo(audioInfo);
        // Screen
        userLog.setScreenInfo(screenInfo);
        // GPS
        userLog.setGpsInfo(gpsInfo);
        // Network Connectivity
        userLog.setConnInfo(connInfo);

        reader.close();
        return userLog;
    }

    private void parseCpu(String s){
        final String CPU_CHECK = "running";
        String line = s.trim();
        String[] tempInfo;
        String time;
        int idx = line.indexOf(CPU_CHECK);

        // Parsing time
        if(line.charAt(0)=='0'){
            time = "0h 0m 0s 0ms";
        }else{
            time = insertSpace(line.substring(1, line.indexOf("(")-1));
        }
        time = cpuInfo.padZero(makeTimeArray(time));

        if(line.charAt(idx-1)=='+'){
            tempInfo = new String[3]; // start, end, duration
            tempInfo[0] = time; // set start
            cpuInfo.addInfoArr(tempInfo);
        }else{
            setTime(cpuInfo, time); // set prev end, calculate and set duration
        }
    }

    private void parseAudio(String s){
        final String AUDIO_CHECK = "audio";
        String line = s.trim();
        String[] tempInfo;
        String time;
        int idx = line.indexOf(AUDIO_CHECK);

        // Parsing time
        if(line.charAt(0)=='0'){
            time = "0h 0m 0s 0ms";
        }else{
            time = insertSpace(line.substring(1, line.indexOf("(")-1));
        }
        time = audioInfo.padZero(makeTimeArray(time));

        if(line.charAt(idx-1)=='+'){
            tempInfo = new String[3]; // start, end, duration
            tempInfo[0] = time;
            audioInfo.addInfoArr(tempInfo);
        }else{
            setTime(audioInfo, time);
        }
    }

    private void parseVolumeFore(String s) {
        final String SUMMARY_CHECK = "Historical broadcasts summary";
        final String VOLUME_CHECK = "android.media.EXTRA_VOLUME_STREAM_VALUE=";

        String line = s.trim();
        VolumeHistory[] volumeHistories = audioInfo.getVolumeHistories();
        String type = "";
        int idx = line.indexOf(SUMMARY_CHECK);

        // save the summary type
        if (s.contains(SUMMARY_CHECK)) {
            type = line.substring(idx + SUMMARY_CHECK.length() + 2, line.length() - 2);
            if (type.equals("foreground")) {
                volumeHistories[0].setSummaryType("foreground");
            }
        }

        // count volume level
        if (s.contains(VOLUME_CHECK)) {
            int idxVolume = line.indexOf(VOLUME_CHECK) + VOLUME_CHECK.length();
            int level = Integer.parseInt((line.substring(idxVolume, idxVolume + 1)));
            volumeHistories[0].setVolumeLevel(level);
        }
    }

    private void parseVolumeBack(String s){
        final String SUMMARY_CHECK = "Historical broadcasts summary";
        final String VOLUME_CHECK = "android.media.EXTRA_VOLUME_STREAM_VALUE=";

        String line = s.trim();
        VolumeHistory[] volumeHistories = audioInfo.getVolumeHistories();

        int idx = line.indexOf(SUMMARY_CHECK);

        String type = "";

        // save the summary type
        if(s.contains(SUMMARY_CHECK)){
            type = line.substring(idx+SUMMARY_CHECK.length()+2, line.length()-2);
            if(type.equals("background")){
                volumeHistories[1].setSummaryType("background");
            }
        }

        // count volume level
        if(s.contains(VOLUME_CHECK)){
            int idxVolume = line.indexOf(VOLUME_CHECK) + VOLUME_CHECK.length();
            int level = Integer.parseInt((line.substring(idxVolume, idxVolume+1)));
            volumeHistories[1].setVolumeLevel(level);
        }
    }

    private void parseScreen(String s){
        final String BRIGHTNESS_CHECK = "brightness=";
        String[] tempInfo = new String[4]; // level, start, end, duration
        String line = s.trim();

        String time;

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
                // Assume brightness as MEDIUM when +screen but no brightness
                tempInfo[0] = "medium";
            }else{
                // Assume brightness as DARK when -screen but no brightness
                tempInfo[0] = "dark";
            }
        }else{ // Parse after "brightness ="
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

        setTime(screenInfo, time);
        screenInfo.addInfoArr(tempInfo);
    }

    private void parseGps(String s){
        String line = s.trim();
        String time;
        String[] tempInfo;
        int idx = line.indexOf("gps");

        // Parsing time
        if(line.charAt(0)=='0'){
            time = "0h 0m 0s 0ms";
        }else{
            time = insertSpace(line.substring(1, line.indexOf("(")-1));
        }
        time = gpsInfo.padZero(makeTimeArray(time));

        if(line.charAt(idx-1)=='+'){
            tempInfo = new String[3]; // start, end, duration
            tempInfo[0] = time;
            gpsInfo.addInfoArr(tempInfo);
        }else{
            setTime(gpsInfo, time);
        }
    }

    private void parseDataConn(String s){
        final String DATACONN_CHECK = "data_conn=";
        String line = s.trim();
        String[] tempInfo;
        String time;

        // Parsing time
        if(line.charAt(0)=='0'){
            time = "0h 0m 0s 0ms";
        }else{
            time = insertSpace(line.substring(1, line.indexOf("(")-1));
        }
        time = connInfo.padZero(makeTimeArray(time));

        // Parsing information
        if(line.contains("+mobile_radio") && !line.contains(DATACONN_CHECK)){
            // Assume conn_type = "3g" when +mobile_radio but no "data_conn="
            tempInfo = new String[4]; // type, start, end, duration
            tempInfo[0] = "3g";
            tempInfo[1] = time;
            connInfo.getDataConn().addInfoArr(tempInfo);
        }else if(line.contains(DATACONN_CHECK)) {
            tempInfo = new String[4]; // type, start, end, duration
            int idx = line.indexOf(DATACONN_CHECK) + DATACONN_CHECK.length();
            if (line.charAt(idx) == 'n' || line.charAt(idx) == 'h') { // none or hspa
                tempInfo[0] = "3g";
            } else if (line.charAt(idx) == 'l') { // lte
                tempInfo[0] = "lte";
            }
            tempInfo[1] = time;
            connInfo.getDataConn().addInfoArr(tempInfo);
        }
        setTime(connInfo.getDataConn(), time);


    }

    private void parseWifiConn(String s){
        final String WIFI_CHECK = "wifi_radio";
        String line = s.trim();
        String[] tempInfo;
        String time;

        // Parsing time
        if(line.charAt(0)=='0'){
            time = "0h 0m 0s 0ms";
        }else{
            time = insertSpace(line.substring(1, line.indexOf("(")-1));
        }
        time = connInfo.padZero(makeTimeArray(time));

        int idx = line.indexOf(WIFI_CHECK);
        if(line.charAt(idx-1)=='+'){
            tempInfo = new String[4]; // type, start, end, duration
            tempInfo[0] = "wifi";
            tempInfo[1] = time;
            connInfo.getWifiConn().addInfoArr(tempInfo);
        }else{
            setTime(connInfo.getWifiConn(), time);
        }
    }

    private void parseTimeOnBattery(String s){
        final String TIME_CHECK = "Total run time:";
        String line = s.trim();
        int idx1 = line.indexOf(TIME_CHECK) + TIME_CHECK.length() + 1;
        int idx2 = line.indexOf("realtime")-1;
        String tempTime = line.substring(idx1, idx2);
        String time = info.padZero(makeTimeArray(tempTime));

        userLog.setTotalTime(time);
        setTime(cpuInfo, time);
        setTime(audioInfo, time);
        setTime(screenInfo, time);
        setTime(gpsInfo, time);
        setTime(connInfo.getDataConn(), time);
        setTime(connInfo.getWifiConn(), time);

    }

    // Parse Time
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

    private void setTime(Information infoClass, String time){
        if(infoClass.getInfoArrLength() > 0){
            infoClass.setPrevEndTime(time); // update prev end time

            String startT = infoClass.getPrevStartTime();
            String endT = infoClass.getPrevEndTime();
            String duration = infoClass.calculateDuration(endT, startT); // calculate duration

            infoClass.setDuration(duration);
        }
    }

}

