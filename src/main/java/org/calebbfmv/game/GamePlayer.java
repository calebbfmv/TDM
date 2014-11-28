package org.calebbfmv.game;

import org.bukkit.entity.Player;
import org.calebbfmv.TDM;
import org.calebbfmv.io.PlayerInfoFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Tim [calebbfmv] (9/20/2014) for ${PROJECT_NAME}
 */
public class GamePlayer {

	private UUID uuid;
	private String name;
	private int kills, deaths, wins, loses, coins;
	private PlayerInfoFile infoFile;
	private static Map<UUID, GamePlayer> gamePlayers = new HashMap<>();

	public GamePlayer(Player player) {
		File dir = new File(TDM.getInstance().getDataFolder(), "players");
		if(!dir.exists()) {
			dir.mkdirs();
		}
		this.uuid = player.getUniqueId();
		this.name = player.getName();
		this.kills = 0;
		this.deaths = 0;
		this.wins = 0;
		this.loses = 0;
		this.coins = 0;
		File file = new File(dir, uuid.toString() + ".yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.infoFile = new PlayerInfoFile(file);
		gamePlayers.put(uuid, this);
	}

	public GamePlayer(PlayerInfoFile info) {
		this.infoFile = info;
		this.uuid = info.getUUID();
		this.name = info.getName();
		this.coins = info.getCoins();
		this.wins = info.getWins();
		this.loses = info.getLoses();
		this.deaths = info.getDeaths();
		this.kills = info.getKills();
		gamePlayers.put(uuid, this);
	}

	public static GamePlayer getPlayer(Player player) {
		return gamePlayers.get(player.getUniqueId());
	}

	public void logout(Player player) {
		gamePlayers.remove(player.getUniqueId());
		PlayerInfoFile file = infoFile;
		file.set("name", this.name);
		file.set("coins", this.coins);
		file.set("kills", this.kills);
		file.set("deaths", this.deaths);
		file.set("wins", this.wins);
		file.set("loses", this.loses);
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public PlayerInfoFile getInfoFile() {
		return infoFile;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLoses() {
		return loses;
	}

	public void setLoses(int loses) {
		this.loses = loses;
	}

}
