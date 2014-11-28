package org.calebbfmv.game;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.calebbfmv.enums.*;

import java.util.*;

/**
 * @author Tim [calebbfmv] (9/20/2014) for ${PROJECT_NAME}
 */
public class Team {

    private TeamColor color;
    private String name;
    private ChatColor chatColor;
    private List<UUID> players;

    /**
     *
     * @param color
     * @param name
     * @param chatColor
     */
    public Team(TeamColor color, String name, ChatColor chatColor) {
        this.color = color;
        this.name = name;
        this.chatColor = chatColor;
        this.players = new ArrayList<>();
    }

    public TeamColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public void add(Player player){
        players.add(player.getUniqueId());
    }

    public void remove(Player player){
        players.remove(player.getUniqueId());
    }

    public void eliminate(){
        players.clear();
        //add X to the SB
    }
}
