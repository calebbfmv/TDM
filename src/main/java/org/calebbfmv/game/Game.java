package org.calebbfmv.game;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.calebbfmv.*;
import org.calebbfmv.enums.*;
import org.calebbfmv.util.Messenger;
import org.calebbfmv.util.Messenger.MessageType;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
 * @author Tim [calebbfmv] (9/20/2014) for ${PROJECT_NAME}
 */
public class Game {

	private Arena arena;
	private List<UUID> players;
	private int time, countdownTime;
	private int teamAmount;
	private Messenger messenger;
	private List<Team> teams;
	private Map<UUID, Team> teamGameMap;

	public Game(Arena arena) {
		this.arena = arena;
		this.time = (int) TimeUnit.MINUTES.toSeconds(10);
		this.countdownTime = 30;
		this.players = new ArrayList<>();
		this.teamAmount = 2;
		this.teams = new ArrayList<>();
		this.createNewTeams();
		this.teamGameMap = new HashMap<>();
		this.messenger = new Messenger(this);
	}

	private void createNewTeams() {
		TeamColor[] colors = TeamColor.values();
		List<TeamColor> teamColors = new ArrayList<>(Arrays.asList(colors));
		Random random = new Random();
		for (int i = 0; i < teamAmount; i++) {
			TeamColor color = teamColors.get(random.nextInt(colors.length));
			String name = color.name().substring(0, 1).toUpperCase() + color.name().substring(1).toLowerCase();
			ChatColor chatColor = color.getChatColor();
			Team team = new Team(color, name, chatColor);
			teams.add(team);
			teamColors.remove(color);
		}
	}

	public Team getTeam(int i) {
		return teams.get(i);
	}

	public void countdown() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(time == 0) {
					cancel();
					getGamePlayers().stream().forEach(player -> {
						messenger.message(player, "Game Starting!", MessageType.SUCCESS);
						player.setHealth(20.0D);
						player.setFoodLevel(20);
						player.setGameMode(GameMode.SURVIVAL);
						player.setLevel(0);
						player.setExp(1F);
						//handle giving kits
					});
					start();
					return;
				}
				if(time % 5 == 0 || time < 10) {
					messenger.info("Game will be starting in: " + (time < 10 ? "0" + time : time) +
						(time == 1 ? "second" : "seconds"));
				}
				time--;
			}
		}.runTaskTimer(TDM.getInstance(), 0L, 20L);
	}

	public void start() {

	}

	public void stop() {

	}

	public void addPlayer(Player player) {

	}

	public void removePlayer(Player player) {

	}

	public List<Player> getGamePlayers() {
		return players.stream().map(Bukkit::getPlayer).collect(Collectors.toList());
	}

	public List<UUID> getPlayers() {
        return players;
	}


}
