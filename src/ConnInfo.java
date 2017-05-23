import java.util.ArrayList;


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

    public void printConnContents(){
        ArrayList<String[]> trace3g = find3gTrace();
        ArrayList<String[]> traceLte = findLteTrace();
        ArrayList<String[]> traceWifi = wifiConn.getWifiConnInfoArr();

        System.out.println("---------------- Connectivity Trace ----------------");
        System.out.println("<<<<<<<<<<<<< 3g Trace >>>>>>>>>>>>");
        printNamedTrace(trace3g);
        System.out.println();
        System.out.println("<<<<<<<<<<<<< lte Trace >>>>>>>>>>>");
        printNamedTrace(traceLte);
        System.out.println();
        System.out.println("<<<<<<<<<<<<< wifi Trace >>>>>>>>>>");
        printNamedTrace(traceWifi);
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
