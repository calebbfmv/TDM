package org.calebbfmv.enums;

import org.bukkit.ChatColor;
import org.bukkit.Color;

/**
 * @author Tim [calebbfmv] (9/20/2014) for ${PROJECT_NAME}
 */
public enum TeamColor {

    RED(Color.RED, ChatColor.DARK_RED),
    BLUE(Color.BLUE, ChatColor.BLUE),
    BLACK(Color.BLACK, ChatColor.BLACK),
    YELLOW(Color.YELLOW, ChatColor.YELLOW),
    GREEN(Color.GREEN, ChatColor.GREEN),
    ORANGE(Color.ORANGE, ChatColor.GOLD),
    PURPLE(Color.PURPLE, ChatColor.LIGHT_PURPLE),
    NAVY(Color.NAVY, ChatColor.DARK_BLUE);

    private Color color;
    private ChatColor chatColor;

    private TeamColor(Color color, ChatColor chatColor){
        this.chatColor = chatColor;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public ChatColor getChatColor(){
        return chatColor;
    }
    //➤
}
