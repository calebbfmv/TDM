package org.calebbfmv.kit;

import org.bukkit.entity.Player;

/**
 * @author Tim [calebbfmv] (9/20/2014) for ${PROJECT_NAME}
 */
public class Kit {

	private KitItem[] kitItems;
	private int price;
	private String name, permission;

	public Kit(KitItem[] kitItems, int price, String name, String permission) {
		this.kitItems = kitItems;
		this.price = price;
		this.name = name;
		this.permission = permission;
	}


	public KitItem[] getKitItems() {
		return kitItems;
	}

	public String getName() {
		return name;
	}

	public String getPermission() {
		return permission;
	}

	public void purchase(Player player) {
		for(int i = 0; i < kitItems.length; i++) {
			KitItem item = kitItems[i];
			player.getInventory().setItem(i, item.getItem());
		}
	}
}
