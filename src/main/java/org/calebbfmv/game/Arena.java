package org.calebbfmv.game;

import org.bukkit.entity.Player;
import org.calebbfmv.enums.GameState;
import org.calebbfmv.game.GameMap;

import java.util.*;

/**
 * @author Tim [calebbfmv] (9/20/2014) for ${PROJECT_NAME}
 */
public class Arena {

	private List<GameMap> gameMaps;
	private boolean canEdit;
	private Map<GameMap, Integer> votes;
	private Map<Integer, GameMap> mapIds;
	private List<UUID> voters;
	private GameMap gameMap;
	private GameState state;

	public Arena(List<GameMap> gameMaps, boolean canEdit) {
		this.gameMaps = gameMaps;
		this.canEdit = canEdit;
		this.votes = new LinkedHashMap<>();
		this.gameMap = null;
		this.voters = new ArrayList<>();
		this.mapIds = new LinkedHashMap<>();
		for(int i = 0; i < gameMaps.size(); i++) {
			mapIds.put(i + 1, gameMaps.get(i));
		}
	}

	public void vote(Player player, GameMap map) {
		if(voters.contains(player.getUniqueId())) {

		}
	}

	//============= DEF METHODS ================//

	public List<GameMap> getGameMaps() {
		return gameMaps;
	}

	public boolean canEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public Map<GameMap, Integer> getVotes() {
		return votes;
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}


}
