package org.calebbfmv.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Tim [calebbfmv]
 *         Created by Tim [calebbfmv] on 10/19/2014.
 */
public abstract class Menu {

    private static HashMap<String, Menu> guis = new HashMap<>();
    private String name;
    private Button[] buttons;
    private HashMap<UUID, Inventory> inventories;

    public Menu(String name, Button[] buttons) {
        this.name = name;
        name = getName();
        name = ChatColor.stripColor(name);
        this.buttons = buttons;
        this.inventories = new HashMap<>();
        guis.put(name.toLowerCase(), this);
    }

    public static Menu get(String name) {
        name = name.toLowerCase();
        name = ChatColor.stripColor(name);
        return guis.get(name);
    }

    protected static ItemStack create(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return item;
    }

    protected static ItemStack name(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return item;
    }

    protected static Button create(ItemStack item, String name, Button.ClickExecutor clickExecutor) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return new Button(item, clickExecutor);
    }

    protected static Button create(ItemStack item, Button.ClickExecutor ce) {
        return new Button(item, ce);
    }

    public String getName() {
        return ChatColor.translateAlternateColorCodes('&', name);
    }

    public void update(Player player) {
        InventoryView view = player.getOpenInventory();
        if (view == null) {
            return;
        }
        if (!view.getTitle().equalsIgnoreCase(this.getName())) {
            return;
        }
        Inventory inventory = view.getTopInventory();
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == null) {
                continue;
            }
            ItemStack item = buttons[i].getItemStack();
            inventory.setItem(i, item);
        }
    }

    public void open(Player player) {
        int size = (buttons.length + 8) / 9 * 9;
        Inventory inventory = Bukkit.createInventory(player, size, getName());
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == null) {
                continue;
            }
            ItemStack item = buttons[i].getItemStack();
            inventory.setItem(i, item);
        }
        player.openInventory(inventory);
    }

    public Button[] getButtons() {
        return buttons;
    }

    public void setButtons(Button[] buttons) {
        this.buttons = buttons;
    }

    public int getSize(Player player) {
        if (inventories.get(player.getUniqueId()) == null) {
            return -1;
        }
        return inventories.get(player.getUniqueId()).getSize();
    }

    public Inventory getInventory(Player player) {
        return inventories.get(player.getUniqueId());
    }

    public Button getButton(int slot) {
        try {
            return buttons[slot];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public void setButton(int slot, Button button) {
        try {
            buttons[slot] = button;
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }


    public abstract void onClose();
}
