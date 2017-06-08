import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        while(!(s = reader.readLine()).contains("UPTIME (uptime)")){
            if (s.contains("== dumpstate:") && !s.contains(" done")){
                parseDumpTime(s);
            }else if(s.contains("Build fingerprint")){
                Pattern pattern = Pattern.compile(":[0-9]\\.[0-9]");
                Matcher matcher = pattern.matcher(s);
                if(matcher.find()){
                    userLog.setAndroidVer(matcher.group().substring(1));
                }
            }
        }

        if(userLog.getAndroidVer().charAt(0) == '6'){
            parseMarshmallow(reader);
        }else {
            parseNougat(reader);
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

    public void parseMarshmallow(BufferedReader reader) throws IOException {
        String s;

        while ((s = reader.readLine()) != null) {
            if(s.contains("Battery History")) {
                while (!(s = reader.readLine()).contains("Per-PID Stats:")) {
                    if (s.contains("gps")) {
                        parseGps(s);
                    }
                    if (s.contains("brightness=") || s.contains("screen ")) {
                        parseScreen(s);
                    }
                    if (s.contains("mobile_radio") || s.contains("data_conn=")) {
                        parseDataConn(s);
                    }
                    if (s.contains("wifi_radio")) {
                        parseWifiConn(s);
                    }
                    if (s.contains("+running") || s.contains("-running")) {
                        parseCpu(s);
                    }
                    if (s.contains("+audio") || s.contains("-audio")) {
                        parseAudio(s);
                    }
                }
            }else if(s. contains("--------- beginning of main")){
                while(!s.contains("[logcat:") &&!s.contains("elapsed]")){
                    parseBlueConn(s);
                    s = reader.readLine();
                }
            } else if(s.contains("Historical broadcasts summary [foreground]:")){
                while(!s.contains("Historical broadcasts [background]:") &&
                        !s.contains("Delayed Historical broadcasts [foreground]:")){
                    parseVolume(s);
                    s = reader.readLine();
                }
            }else if(s.contains("Historical broadcasts summary [background]:")){
                while(!s.contains("Sticky broadcasts for user -1:") &&
                        !s.contains("Aborted Historical broadcasts [background]:")){
                    parseVolume(s);
                    s = reader.readLine();
                }
            }else if(s.contains("Total run time:")){
                parseTotalRuntime(s);
            }
        }
    }

    public void parseNougat(BufferedReader reader) throws IOException {
        String s;

        while ((s = reader.readLine()) != null) {
            if(s.contains("Battery History")) {
                while (!(s = reader.readLine()).contains("Per-PID Stats:")) {
                    if (s.contains("gps")) {
                        parseGps(s);
                    }
                    if (s.contains("brightness=") || s.contains("screen ")) {
                        parseScreen(s);
                    }
                    if (s.contains("mobile_radio") || s.contains("data_conn=")) {
                        parseDataConn(s);
                    }
                    if (s.contains("wifi_radio")) {
                        parseWifiConn(s);
                    }
                    if (s.contains("+running") || s.contains("-running")) {
                        parseCpu(s);
                    }
                    if (s.contains("+audio") || s.contains("-audio")) {
                        parseAudio(s);
                    }
                }
            }else if(s. contains("------ SYSTEM LOG")){
                while(!s.contains("the duration of 'SYSTEM LOG' ------")){
                    parseBlueConn(s);
                    s = reader.readLine();
                }
            } else if(s.contains("Historical broadcasts summary [foreground]:")){
                while(!s.contains("Historical broadcasts [background]:") &&
                        !s.contains("Delayed Historical broadcasts [foreground]:")){
                    parseVolume(s);
                    s = reader.readLine();
                }
            }else if(s.contains("Historical broadcasts summary [background]:")){
                while(!s.contains("Sticky broadcasts for user -1:") &&
                        !s.contains("Aborted Historical broadcasts [background]:")){
                    parseVolume(s);
                    s = reader.readLine();
                }
            }else if(s.contains("Total run time:")){
                parseTotalRuntime(s);
            }
        }
    }

    private void parseDumpTime(String s){
        String line = s.trim();

        int blankIdx = line.indexOf(" ");
        line = line.substring(blankIdx+1);

        blankIdx = line.indexOf(" ");
        line = line.substring(blankIdx+1);

        blankIdx = line.indexOf(" ");
        line = line.substring(blankIdx+1)+":00";

        userLog.setLogEndTime(line);
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

    private void parseVolume(String s) {
        String line = s.trim();
        int[] volumeHistories = audioInfo.getVolumeHistories();

        // count volume level
        Pattern pattern = Pattern.compile("android\\.media\\.EXTRA\\_VOLUME\\_STREAM\\_VALUE\\=[0-9]++");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String volumeStr = matcher.group();
            int level = Integer.parseInt(volumeStr.substring(volumeStr.indexOf('=')+1));
            volumeHistories[level]+=1;
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
            if (line.charAt(idx) == 'n' || line.charAt(idx) == 'h'
                    || line.charAt(idx) == 'u') { // none or hspa or umts
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

    private void parseBlueConn(String s){
        final String BLUE_CHECK = "BluetoothLeScanner:";
        String line = s.trim();
        String[] tempInfo;
        String time;
        int connArrSize = connInfo.getBlueToothConn().getInfoArrLength();

        if(line.contains(BLUE_CHECK)){
            // Parsing time
            int blankIdx = line.indexOf(" ");
            line = line.substring(blankIdx+1);
            blankIdx = line.indexOf(" ");
            time = connInfo.padZero(line.substring(0, blankIdx).split(":|[.]"));
            // time stored in actual time (not exceeded time)

            if(line.contains("Stop")){
                if(connArrSize>0 &&
                        connInfo.getBlueToothConn().getPrevStartTime() != null){
                    setTime(connInfo.getBlueToothConn(), time);
                }
            }else if(line.contains("Start")){
                if(connInfo.getBlueToothConn().checkStartTime()){
                    tempInfo = new String[4]; // type, start, end, duration
                    tempInfo[0] = "bluetooth";
                    tempInfo[1] = time;
                    connInfo.getBlueToothConn().addInfoArr(tempInfo);
                }
            }
        }
    }

    private void parseTotalRuntime(String s){
        final String TIME_CHECK = "Total run time:";
        String line = s.trim();
        int idx1 = line.indexOf(TIME_CHECK) + TIME_CHECK.length() + 1;
        int idx2 = line.indexOf("realtime")-1;
        String tempTime = line.substring(idx1, idx2);
        String totalTime = info.padZero(makeTimeArray(tempTime));
        userLog.setTotalTime(totalTime); // set total runtime

        // calculate the log start time using the DUMP time and the total runtime
        // DUMP time(log end time) is saved in the format (00:00:00).
        // startTime means the start time when the log has been recorded.
        String endTime = info.padZero(userLog.getLogEndTime().split(":"));
        String totalDuration = userLog.getTotalTime();
        String startTime = info.subtractTimes(endTime, totalDuration);

        // save the start time in format (00:00:00:000)
        userLog.setLogStartTime(changeFormat(startTime));

        // set the final end time on blanked information
        setTime(cpuInfo, totalTime);
        setTime(audioInfo, totalTime);
        setTime(screenInfo, totalTime);
        setTime(gpsInfo, totalTime);
        setTime(connInfo.getDataConn(), totalTime);
        setTime(connInfo.getWifiConn(), totalTime);

        ////////Change Bluetooth time/////////
        // Due to the log of bluetooth saved in actual time
        if(connInfo.getBlueToothConn().getInfoArrLength()>0){

            connInfo.getBlueToothConn().setPrevEndTime(totalTime);
            connInfo.getBlueToothConn().changeTimeFormat(startTime);
            setTime(connInfo.getBlueToothConn(), totalTime);
        }
    }

    // Parse Time
    private String[] makeTimeArray(String timeString) {
        // expected input: 0h 0m 0s 0ms
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

    private void setTime(Information info, String time){
        if(info.getInfoArrLength() > 0){
            info.setPrevEndTime(time); // update prev end time

            String startT = info.getPrevStartTime();
            String endT = info.getPrevEndTime();
            String duration = info.subtractTimes(endT, startT); // calculate duration

            info.setDuration(duration);
        }
    }

    private String changeFormat(String orinal){
        // expected input format: 00h 00m 00s 00ms
        // This method changes "h m s ms" format into ":" format
        String[] timeArray = makeTimeArray(orinal);
        return timeArray[0]+":"+timeArray[1]+":"+timeArray[2]+":"+timeArray[3];
    }

}

