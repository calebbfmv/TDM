package net.craftgasm.enums;

import org.bukkit.Color;

/**
 * Created by Tim [calebbfmv] on 9/20/2014.
 */
public enum TeamColor {

    RED(Color.RED),
    BLUE(Color.BLUE),
    BLACK(Color.BLACK),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    ORANGE(Color.ORANGE),
    PURPLE(Color.PURPLE),
    NAVY(Color.NAVY);

    private Color color;

    private TeamColor(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    //➤ 
}
