package org.calebbfmv.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.calebbfmv.game.Game;

/**
 * @author Tim [calebbfmv] (11/23/2014) for TDM
 */
public class Messenger {

	public enum MessageType {

		ERROR(ChatColor.RED + "TDM", ChatColor.WHITE.toString() + ChatColor.ITALIC),
		SUCCESS(ChatColor.GREEN + "TDM", ChatColor.YELLOW.toString());

		private String prefix, suffix;

		private MessageType(String prefix, String suffix) {
			this.prefix = ChatColor.GRAY + "[" + ChatColor.BOLD + prefix + ChatColor.GRAY + "] ";
			this.suffix = suffix;
		}

		public void sendMessage(Player player, String message) {
			String pref = ChatColor.GREEN.toString() + ChatColor.BOLD + "➤";
			player.sendMessage(prefix + suffix + pref + suffix + message);
		}

	}

	private Game game;

	public Messenger(Game game) {
		this.game = game;
	}

	public void info(String message) {
		String prefix = ChatColor.GREEN.toString() + ChatColor.BOLD + "➤";
		game.getGamePlayers().stream().forEach(player -> player.sendMessage(prefix + ChatColor.AQUA + message));
	}

	public void announce(String message) {
		String prefix = ChatColor.YELLOW.toString() + ChatColor.BOLD + "➤";
		game.getGamePlayers().stream().forEach(player -> player.sendMessage(prefix + ChatColor.RED + message));
	}

	public void message(Player player, String message, MessageType type) {
		type.sendMessage(player, message);
	}

}
