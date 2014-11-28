package org.calebbfmv.io;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Tim [calebbfmv] (11/20/2014) for TDM
 */
public class PlayerInfoFile {

	private File file;
	private FileConfiguration config;

	public PlayerInfoFile(File file) {
		this.file = file;
		this.config = YamlConfiguration.loadConfiguration(file);
	}

	public File getFile() {
		return file;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return config.getString("name");
	}

	public UUID getUUID() {
		return UUID.fromString(file.getName().replace(".yml", ""));
	}

	public int getKills() {
		return config.getInt("kills");
	}

	public int getDeaths() {
		return config.getInt("deaths");
	}

	public int getWins() {
		return config.getInt("wins");
	}

	public int getLoses() {
		return config.getInt("loses");
	}

	public int getCoins() {
		return config.getInt("coins");
	}

	public void set(String path, Object value) {
		this.config.set(path, value);
		this.save();
	}

}
