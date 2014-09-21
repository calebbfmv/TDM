package net.craftgasm.commands;

import net.craftgasm.TDM;
import org.bukkit.entity.Player;

/**
 * Created by Tim [calebbfmv] on 9/20/2014.
 */
public abstract class ICommand {

    private String command;

    public ICommand(String command){
        this.command = command;
        TDM.getInstance().getCommandHandler().register(command, this);
    }

    public abstract void execute(Player sender, String[] args);
}
