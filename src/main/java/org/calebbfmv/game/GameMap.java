package org.calebbfmv.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * @author Tim [calebbfmv] (11/20/2014) for TDM
 */
public class GameMap {

	private SpawnPoint[] spawns;
	private String name;
	private World world;

	public GameMap(SpawnPoint[] spawns, String name, World world) {
		this.spawns = spawns;
		this.name = name;
		this.world = world;
		this.world.setAutoSave(false);
		this.world.setAnimalSpawnLimit(0);
		this.world.setGameRuleValue("doDayNightCycle", "false");
		this.world.setPVP(true);
	}

	public GameMap(SpawnPoint[] spawns, World world) {
		this(spawns, world.getName(), world);
	}

	public void unloadMap() {
		Bukkit.getServer().unloadWorld(world, false);
	}

	public void loadMap() {
		Bukkit.getServer().createWorld(new WorldCreator(name)).setAutoSave(false);
	}

	public void rollback() {
		unloadMap();
		loadMap();
	}

	public void teleport(Player player, boolean doRandom) {
		if (doRandom) {
			Random random = new Random();
			int next = random.nextInt(spawns.length);
			SpawnPoint spawn = spawns[next];
			if (spawn.isUsed()) {
				for (int i = 0; i < spawns.length; i++) {
					SpawnPoint point = spawns[random.nextInt(spawns.length)];
					if (point.isUsed()) {
						continue;
					}
					spawn = point;
					break;
				}
			}
			spawn.teleport(player);
			return;
		}
		for (SpawnPoint point : spawns) {
			if (point.isUsed()) {
				continue;
			}
			point.teleport(player);
			break;
		}
	}
	//http://imgur.com/gallery/Q9saS

	public class SpawnPoint {

		private Location location;
		private boolean used;

		public SpawnPoint(Location location) {
			this.location = location;
			this.used = false;
		}

		public boolean isUsed() {
			return used;
		}

		public void teleport(Player player) {
			this.used = true;
			player.teleport(location);
		}
	}

}
