package org.calebbfmv.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Tim [calebbfmv] (11/19/2014) for TDM
 */
public class KitItem {

	private ClickExecutor executor;
	private ItemStack item;

	public KitItem(ItemStack item, ClickExecutor executor){
		this.item = item;
		this.executor = executor;
	}

	public KitItem(ItemStack item) {
		this(item, null);
	}

	public void click(Player player) {
		if(executor == null) {
			return;
		}
		executor.onClick(player);
	}

	public ItemStack getItem() {
		return item;
	}

	public interface ClickExecutor {

		public void onClick(Player player);
	}


}
