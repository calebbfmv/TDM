package org.calebbfmv.listeners;

import org.bukkit.event.Listener;
import org.calebbfmv.TDM;

/**
 * @author Tim [calebbfmv] (9/20/2014) for ${PROJECT_NAME}
 */
public class GameListener implements Listener {

	public GameListener() {
		TDM plugin = TDM.getInstance();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
}
