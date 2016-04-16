package JavaBean;

/**
 * Created by Yasic on 2016/4/14.
 */
public class ARPBean {
    private String TargetIP;
    private String GateWay;
    private String TargetMac;
    private String LocalMac;
    private boolean isLocal;

    public ARPBean(String targetIP, String gateWay, String targetMac, String localMac, boolean isLocal) {
        TargetIP = targetIP;
        GateWay = gateWay;
        TargetMac = targetMac;
        LocalMac = localMac;
        this.isLocal = isLocal;
    }

    public String getTargetIP() {
        return TargetIP;
    }

    public String getGateWay() {
        return GateWay;
    }

    public String getTargetMac() {
        return TargetMac;
    }

    public String getLocalMac() {
        return LocalMac;
    }

    public boolean isLocal() {
        return isLocal;
    }
}
