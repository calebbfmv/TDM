package net.craftgasm;

import net.craftgasm.commands.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Tim [calebbfmv] on 9/20/2014.
 */
public class TDM extends JavaPlugin {

    private static TDM instance;
    private CommandHandler commandHandler;

    @Override
    public void onEnable(){
        instance = this;
        this.commandHandler = new CommandHandler(this);
    }

    @Override
    public void onDisable(){

    }

    public static TDM getInstance(){
        return instance;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
