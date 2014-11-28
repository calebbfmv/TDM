package org.calebbfmv.commands;

import org.bukkit.command.*;
import org.calebbfmv.*;

/**
 * @author Tim [calebbfmv]
 */
public abstract class SubCommand {

    public abstract void execute(CommandSender sender, String[] args);

    private String command, permission;
    private String[] description, aliases;

    /**
     * Creates a new SubCommand
     *
     * @param command     The name of the command
     * @param permission  The permission node required
     * @param description The description of what the command does
     * @param aliases     Other names for this command to go by
     */
    public SubCommand(String command, String permission, String[] description, String[] aliases ) {
        this.command = command;
        this.permission = permission;
        this.description = description;
        this.aliases = aliases;
        TDM.getInstance().getCommandHandler().registerCommand(this);
    }

    public String getCommand() {
        return command;
    }

    public String getPermission() {
        return permission;
    }

    public String[] getDescription() {
        return description;
    }

    public String descToString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.getDescription().length; i++) {
            builder.append(this.description[i]);
            if ((i + 1) != this.getDescription().length) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public String[] getAliases() {
        return aliases;
    }
}
