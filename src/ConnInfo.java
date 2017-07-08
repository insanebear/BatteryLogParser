import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Youlim Jung on 2017-04-30.
 */
public class ConnInfo extends Information{
    private DataConn dataConn;
    private WifiConn wifiConn;
    private BlueToothConn blueToothConn;

    public ConnInfo(){
        dataConn = new DataConn();
        wifiConn = new WifiConn();
        blueToothConn = new BlueToothConn();
    }

    public DataConn getDataConn() {
        return dataConn;
    }

    public WifiConn getWifiConn() {
        return wifiConn;
    }

    public BlueToothConn getBlueToothConn() {
        return blueToothConn;
    }

    public void printConnContents(BufferedWriter out) throws IOException {
        ArrayList<String[]> trace3g = find3gTrace();
        ArrayList<String[]> traceLte = findLteTrace();
        ArrayList<String[]> traceWifi = wifiConn.getWifiConnInfoArr();
        ArrayList<String []> traceBlue = blueToothConn.getBlueToothConnInfoArr();

        System.out.println("---------------- Connectivity Trace ----------------");
        out.write("---------------- Connectivity Trace ----------------");out.newLine();

        System.out.println("<<<<<<<<<<<<< 3g Trace >>>>>>>>>>>>");
        out.write("<<<<<<<<<<<<< 3g Trace >>>>>>>>>>>>");out.newLine();

        printNamedTrace(out, trace3g);

        System.out.println();
        out.newLine();

        System.out.println("<<<<<<<<<<<<< lte Trace >>>>>>>>>>>");
        out.write("<<<<<<<<<<<<< lte Trace >>>>>>>>>>>");out.newLine();

        printNamedTrace(out, traceLte);

        System.out.println();
        out.newLine();

        System.out.println("<<<<<<<<<<<<< wifi Trace >>>>>>>>>>");
        out.write("<<<<<<<<<<<<< wifi Trace >>>>>>>>>>");out.newLine();

        printNamedTrace(out, traceWifi);

        System.out.println("<<<<<<<<<<<<< bluetooth Trace >>>>>>>>>>");
        out.write("<<<<<<<<<<<<< bluetooth Trace >>>>>>>>>>");out.newLine();

        printNamedTrace(out, traceBlue);
    }

    public ArrayList<String[]> find3gTrace (){
        ArrayList<String[]> dataInfoArr = dataConn.getDataConnInfoArr();
        ArrayList<String[]> trace3g = new ArrayList<>();

        if(dataInfoArr.size()>0){
            for (int i=0; i<dataInfoArr.size(); i++) {
                String[] trace = dataInfoArr.get(i);
                if (trace[0].equals("3g")) {
                    trace3g.add(trace);
                }
            }
        }
        return trace3g;
    }

    public ArrayList<String[]> findLteTrace(){
        ArrayList<String[]> dataInfoArr = dataConn.getDataConnInfoArr();
        ArrayList<String[]> traceLte = new ArrayList<>();

        if(dataInfoArr.size()>0){
            for (int i=0; i<dataInfoArr.size(); i++) {
                String[] trace = dataInfoArr.get(i);
                if (trace[0].equals("lte")) {
                    traceLte.add(trace);
                }
            }
        }
        return traceLte;
    }

}
