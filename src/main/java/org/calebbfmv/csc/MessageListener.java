package org.calebbfmv.csc;

import org.calebbfmv.*;
import org.calebbfmv.csc.packet.*;
import redis.clients.jedis.*;

/**
 * @author Tim [calebbfmv]
 *         Created by Tim [calebbfmv] on 11/12/2014.
 */
public class MessageListener extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        if (! channel.equalsIgnoreCase(JedisManager.CHANNEL)) {
            return;
        }
        if (! message.contains(":")) {
            TDM.getInstance().getLogger().warning("Hmmmm, seems like the newest Jedis message (" + message + ") was not a valid message.");
            return;
        }
        String[] str = message.split(":");
        String packetRaw = str[0];
        String msg = str[1];
        Packet packet = Packet.getPacket(packetRaw);
        if (packet == null) {
            TDM.getInstance().getLogger().warning("Hmmmm, seems like the newest Jedis Packet (" + packetRaw + ") was not a valid packet.");
            return;
        }
        packet.handle(msg);
    }

    @Override
    public void onPMessage(String s, String s2, String s3) {

    }

    @Override
    public void onSubscribe(String s, int i) {

    }

    @Override
    public void onUnsubscribe(String s, int i) {

    }

    @Override
    public void onPUnsubscribe(String s, int i) {

    }

    @Override
    public void onPSubscribe(String s, int i) {

    }
}
