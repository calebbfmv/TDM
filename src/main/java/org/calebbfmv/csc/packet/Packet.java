package org.calebbfmv.csc.packet;

import org.calebbfmv.*;

import java.util.*;

/**
 * @author Tim [calebbfmv]
 */
public abstract class Packet {

    private String name;
    private int lastId = 0;

    private static Map<String, Packet> packets = new HashMap<>();

    public Packet(String name) {
        this.name = name;
        this.lastId = lastId + 1;
        packets.put(name, this);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return lastId;
    }

    public abstract void write();

    public abstract void handle(String message);

    public static Packet getPacket(String name) {
        return packets.get(name);
    }

    protected void send(String message) {
        TDM.getInstance().getJedisManager().sendInfo(this, message);
    }
}
