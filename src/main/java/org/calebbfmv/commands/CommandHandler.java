package org.calebbfmv.commands;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.calebbfmv.*;

import java.util.*;

/**
 * @author Tim [calebbfmv]
 */
public class CommandHandler implements Listener {

    private static Map<String, SubCommand> commands = new HashMap<>();
    private static Map<String, SubCommand> commandsByAlias = new HashMap<>();

    public CommandHandler() {
        TDM plugin = TDM.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void registerCommand(SubCommand subCommand) {
        commands.put(subCommand.getCommand(), subCommand);
        if (subCommand.getAliases() != null) {
            List<String> aliases = Arrays.asList(subCommand.getAliases());
            aliases.stream().forEach(alias -> commandsByAlias.put(alias, subCommand));
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
	    String base = event.getMessage().replace("/", "");
	    Player sender = event.getPlayer();
	    String label;
	    String[] args = null;
	    if (base.contains(" ")) {
		    String[] split = base.split(" ");
		    label = split[0];
		    String[] rest = new String[split.length - 1];
		    //noinspection ManualArrayCopy
		    for (int i = 1; i < split.length; i++) {
			    rest[i - 1] = split[i];
		    }
		    args = rest;
	    } else {
		    label = base;
	    }
	    if (commands.get(label) != null) {
		    event.setCancelled(true);
		    SubCommand subCommand = commands.get(label);
		    if (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission())) {
			    return;
		    }
		    subCommand.execute(sender, args);
		    return;
	    }
	    if (commandsByAlias.get(label) != null) {
		    event.setCancelled(true);
		    SubCommand subCommand = commandsByAlias.get(label);
		    if (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission())) {
			    return;
		    }
		    subCommand.execute(sender, args);
	    }
    }
}
