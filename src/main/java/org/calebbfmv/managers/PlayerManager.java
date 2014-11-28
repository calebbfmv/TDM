package org.calebbfmv.managers;

import org.bukkit.entity.Player;
import org.calebbfmv.TDM;
import org.calebbfmv.game.GamePlayer;
import org.calebbfmv.io.PlayerInfoFile;

import java.io.File;

/**
 * @author Tim [calebbfmv] (11/20/2014) for TDM
 */
public class PlayerManager {

	public void load(Player player) {
		File dir = new File(TDM.getInstance().getDataFolder(), "players");
		if(!dir.exists()) {
			new GamePlayer(player);
			return;
		}
		File file = new File(dir, player.getUniqueId().toString() + ".yml");
		if(!file.exists()) {
			new GamePlayer(player);
			return;
		}
		PlayerInfoFile infoFile = new PlayerInfoFile(file);
		new GamePlayer(infoFile);
	}

	public void save(Player player) {
		GamePlayer gamePlayer = GamePlayer.getPlayer(player);
		if(gamePlayer == null) {
			return;
		}
		gamePlayer.logout(player);
	}

}
