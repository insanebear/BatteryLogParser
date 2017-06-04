/**
 * Created by Youlim Jung on 2017-04-29.
 */

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        // Folder name where input files are contained.
        File dir = new File(args[0]);
        ArrayList<UserLog> uLogList = new ArrayList<>();

        for(File file : dir.listFiles()){
            if(file.isDirectory()){
                System.out.println("Pass Directory for now");
            }else{
                Parser parser = new Parser(file);
                uLogList.add(parser.parseLog());
            }
        }
        System.out.println("The number of inputs: "+ uLogList.size());
        for (UserLog uLog : uLogList) {
            System.out.println("============================================================ "+
                    uLog.getFilename()+
                    " ============================================================");
            System.out.println("Android Version: "+uLog.getAndroidVer());
            System.out.println("Log start from "+ uLog.getLogStartTime()+" to "
                    +uLog.getLogEndTime());
            uLog.printTotalTime();
            System.out.println();
            uLog.getCpuInfo().printCpuContents();
            uLog.getConnInfo().printConnContents();
            uLog.getScreenInfo().printScreenContents();
            uLog.getGpsInfo().printGpsContents();
            uLog.getAudioInfo().printAudioContents();

        }
    }

}