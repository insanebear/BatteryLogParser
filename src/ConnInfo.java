import java.util.ArrayList;


/**
 * Created by Youlim Jung on 2017-04-30.
 */
public class ConnInfo extends Information {
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

    public void setDataConn(DataConn dataConn) {
        this.dataConn = dataConn;
    }

    public WifiConn getWifiConn() {
        return wifiConn;
    }

    public void setWifiConn(WifiConn wifiConn) {
        this.wifiConn = wifiConn;
    }

    public BlueToothConn getBlueToothConn() {
        return blueToothConn;
    }

    public void setBlueToothConn(BlueToothConn blueToothConn) {
        this.blueToothConn = blueToothConn;
    }
}
