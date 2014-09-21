package net.craftgasm.commands;

import net.craftgasm.TDM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Tim [calebbfmv] on 9/20/2014.
 */
public class CommandHandler implements CommandExecutor {

    private HashMap<String, ICommand> subCommands = new HashMap<>();
    private TDM plugin;

    public CommandHandler(TDM plugin){
        this.plugin = plugin;
    }

    public void register(String command, ICommand clazz){
        subCommands.put(command, clazz);
    }

    public ICommand getCommand(String cmd){
        return subCommands.get(cmd);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        ICommand iCommand = getCommand(command.getLabel());
        if(iCommand == null){
            //Messenger.send(sender, Message.NO_COMMAND_FOUND(command.getLabel()))
            return true;
        }
        iCommand.execute((Player) sender, strings);
        return true;
    }
}
