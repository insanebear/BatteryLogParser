/**
 * Created by Youlim Jung on 2017-04-29.
 */

import java.io.*;
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

        BufferedWriter out = new BufferedWriter(new FileWriter("parseResult.txt"));
        for (UserLog uLog : uLogList) {

            uLog.printFilename(out);
            uLog.printAndroidVer(out);
            uLog.printTotalTime(out);

            uLog.getCpuInfo().printCpuContents(out);
            uLog.getConnInfo().printConnContents(out);
            uLog.getScreenInfo().printScreenContents(out);
            uLog.getGpsInfo().printGpsContents(out);
            uLog.getAudioInfo().printAudioContents(out);

        }
    }

}