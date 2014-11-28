package org.calebbfmv.csc;

import org.bukkit.configuration.file.FileConfiguration;
import org.calebbfmv.TDM;
import org.calebbfmv.csc.packet.Packet;
import redis.clients.jedis.Jedis;

/**
 * @author Tim [calebbfmv]
 *         Created by Tim [calebbfmv] on 11/12/2014.
 */
public class JedisManager {

	private Jedis jedis;
	public static String CHANNEL = "NationsCSC";

	public JedisManager(FileConfiguration config) {
		String host = config.getString("host");
		String password = config.getString("password");
		this.jedis = new Jedis(host);
		try {
			jedis.auth(password);
		} catch (Exception e) {
			TDM.getInstance().getLogger().info("No password has been set on this Jedis instance, you may want to fix that.");
		}
		new Thread(() -> {
			Jedis subscriber = new Jedis(host);
			try {
				subscriber.auth(password);
			} catch (Exception e) {
				TDM.getInstance().getLogger().info("No password has been set on this Jedis instance, you may want to fix that.");
			}
			subscriber.subscribe(new MessageListener(), CHANNEL);
		}).start();
	}

	public void sendInfo(Packet packet, String message) {
		jedis.publish(CHANNEL, packet.getName() + ":" + message);
	}

}
