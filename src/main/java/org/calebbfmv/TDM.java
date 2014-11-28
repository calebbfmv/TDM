package org.calebbfmv;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.calebbfmv.commands.CommandHandler;
import org.calebbfmv.csc.JedisManager;
import org.calebbfmv.managers.ArenaManager;
import org.calebbfmv.managers.GameManager;
import org.calebbfmv.managers.KitManager;
import org.calebbfmv.managers.PlayerManager;

import java.io.File;

/**
 * @author Tim [calebbfmv] (9/20/2014) for ${PROJECT_NAME}
 */
public class TDM extends JavaPlugin {

	private static TDM instance;
	private CommandHandler commandHandler;
	private JedisManager jedisManager;
	private PlayerManager playerManager;
	private KitManager kitManager;
	private ArenaManager arenaManager;
	private GameManager gameManager;

	@Override
	public void onEnable() {
		instance = this;
		this.commandHandler = new CommandHandler();
		this.playerManager = new PlayerManager();
		this.arenaManager = new ArenaManager();
		this.gameManager = new GameManager();
		this.kitManager = new KitManager();
		File file = new File(getDataFolder(), "jedis.yml");
		if (!file.exists()) {
			this.saveResource("jedis.yml", true);
		}
		this.jedisManager = new JedisManager(YamlConfiguration.loadConfiguration(file));
	}

	@Override
	public void onDisable() {

	}

	public static TDM getInstance() {
		return instance;
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	public JedisManager getJedisManager() {
		return jedisManager;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public KitManager getKitManager() {
		return kitManager;
	}

	public ArenaManager getArenaManager() {
		return arenaManager;
	}

	public GameManager getGameManager() {
		return gameManager;
	}
}
