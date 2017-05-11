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
            Parser parser = new Parser(file);
            uLogList.add(parser.parseLog());
        }

        for (UserLog uLog : uLogList) {
            System.out.println("---------------- "+ uLog.getFilename()+" ----------------");
            uLog.getScreenContents();
            uLog.getGpsInfo();
        }

    }

}