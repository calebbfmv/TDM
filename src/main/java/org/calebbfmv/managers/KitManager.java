package org.calebbfmv.managers;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.calebbfmv.TDM;
import org.calebbfmv.kit.Kit;
import org.calebbfmv.kit.KitItem;
import org.calebbfmv.util.Util;
import org.nationsmc.itemutils.ArmorSlot;
import org.nationsmc.itemutils.GlowEnchant;
import org.nationsmc.itemutils.ItemBuilder;
import org.nationsmc.itemutils.WrappedEnchantment;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Tim [calebbfmv] (9/20/2014) for ${PROJECT_NAME}
 */
public class KitManager {

	private Map<String, Kit> kits = new HashMap<>();
	private Map<UUID, Kit> playerKits = new HashMap<>();
	private File folder;
	private List<File> fileList;

	public KitManager(TDM plugin) {
		this.folder = new File(plugin.getDataFolder(), "kits");
		boolean shouldLoad = true;
		if (!folder.exists()) {
			folder.mkdirs();
			shouldLoad = false;
		}
		this.fileList = new ArrayList<>();
		if (shouldLoad) {
			load();
		}
	}

	public void load() {
		File[] files = folder.listFiles();
		if (files == null) {
			System.out.println("ERROR: No files found! (KL)");
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				System.out.println("ERROR: " + file.getName() + " is a folder. Get this out of here!");
				continue;
			}
			fileList.add(file);
		}
		File[] configs = fileList.toArray(new File[fileList.size()]);
		for (File file : configs) {
			if (!file.getName().endsWith(".yml")) {
				System.out.println("ERROR: " + file.getName() + " is not .yml. Get this out of here!");
				continue;
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			List<KitItem> kitItems = new ArrayList<>();
			int price = config.getInt("price");
			String permission = config.getString("permission");
			ConfigurationSection section = config.getConfigurationSection("items");
			kitItems.addAll(section.getKeys(false).stream().map(s -> readItem(section, s)).collect(Collectors.toList()));
			new Kit(kitItems.toArray(new KitItem[kitItems.size()]),
				price,
				file.getName().replace(".yml", ""),
				permission);
		}
	}

	private KitItem readItem(ConfigurationSection section, String base) {
		if(section == null) {
			return null;
		}
		String name = section.getString(base);
		String type = section.getString(base + "type");
		List<String> lore = new ArrayList<>();
		//noinspection Convert2streamapi
		for(String s : section.getStringList(base + ".lore")) {
			lore.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		int amount = section.getInt(base + ".amount", 1);
		byte data = (byte) section.getDouble(base + ".data", 0);
		ItemStack itemStack = new ItemStack(Material.matchMaterial(type), amount, data);
		ArmorSlot slot = ArmorSlot.getFromVar(section.getString(base + ".armorSlot"));
		ItemBuilder builder = ItemBuilder.wrap(itemStack);
		if(slot != null) {
			builder = ItemBuilder.wrap(itemStack, slot);
		}
		if(section.getStringList(base + ".enchants") != null) {
			List<String> enchants = section.getStringList(base + ".enchants");
			WrappedEnchantment[] enchantments = new WrappedEnchantment[enchants.size()];
			for (int i = 0; i < enchantments.length; i++) {
				String s = enchants.get(i);
				String[] str = s.split(":");
				String enchantName = str[0];
				if (enchantName.equalsIgnoreCase("glow")) {
					enchantments[i] = new WrappedEnchantment(GlowEnchant.getGlowEnchant());
					continue;
				}
				int level = Integer.parseInt(str[1]);
				Enchantment enchantment;
				try {
					enchantment = Enchantment.getByName(enchantName);
				} catch (Exception e) {
					enchantment = Util.getEnchantmentName(enchantName);
				}
				if (enchantment != null) {
					enchantments[i] = new WrappedEnchantment(enchantment, level);
				}
			}
			builder.enchant(enchantments);
		}
		builder.name(ChatColor.translateAlternateColorCodes('&', name));
		builder.lore(lore.toArray(new String[lore.size()]));
		if(section.getString(base + ".color") != null) {
			String colorString = section.getString(base + ".color");
			int r = Integer.parseInt(colorString.split(", ")[0]);
			int g = Integer.parseInt(colorString.split(", ")[1]);
			int b = Integer.parseInt(colorString.split(", ")[2]);
			Color color = Color.fromRGB(r, g, b);
			builder.color(color);
		}
		return new KitItem(builder.build());
	}

	public void give(Player player, Kit kit) {
		playerKits.put(player.getUniqueId(), kit);
		kit.purchase(player);
	}
}
