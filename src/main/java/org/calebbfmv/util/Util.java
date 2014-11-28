package org.calebbfmv.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;

/**
 * @author Tim [calebbfmv]
 *         Created by Tim [calebbfmv] on 11/16/2014.
 */
public class Util {

	public static String toString(Location location) {
		return location.getWorld().getName() + ", " +
			location.getBlockX() + ", " +
			location.getBlockY() + ", " +
			location.getBlockZ() + ", " +
			location.getYaw() + ", " +
			location.getPitch();
	}

	public static Location fromString(String s) {
		if (s == null) {
			return null;
		}
		String[] str = s.split(", ");
		World world = Bukkit.getWorld(str[0]);
		int x = parse(str[1]);
		int y = parse(str[2]);
		int z = parse(str[3]);
		float yaw = Float.parseFloat(str[4]);
		float pitch = Float.parseFloat(str[5]);
		return new Location(world, x, y, z, yaw, pitch);
	}

	public static int parse(String s) {
		return Integer.parseInt(s);
	}

	public static Enchantment getEnchantmentName(String friendlyName) {
		if (friendlyName != null) {
			if (friendlyName.equalsIgnoreCase("Sharpness") || friendlyName.equalsIgnoreCase("Sharp"))
				return Enchantment.DAMAGE_ALL;
			else if (friendlyName.equalsIgnoreCase("Bane of Arthropods") || friendlyName.equalsIgnoreCase("Arthropods") || friendlyName.equalsIgnoreCase("Bane") || friendlyName.equalsIgnoreCase("Arthro"))
				return Enchantment.DAMAGE_ARTHROPODS;
			else if (friendlyName.equalsIgnoreCase("Smite") || friendlyName.equalsIgnoreCase("Undead"))
				return Enchantment.DAMAGE_UNDEAD;
			else if (friendlyName.equalsIgnoreCase("Power"))
				return Enchantment.ARROW_DAMAGE;
			else if (friendlyName.equalsIgnoreCase("Flame") || friendlyName.equalsIgnoreCase("Flames"))
				return Enchantment.ARROW_FIRE;
			else if (friendlyName.equalsIgnoreCase("Infinite") || friendlyName.equalsIgnoreCase("Infinity"))
				return Enchantment.ARROW_INFINITE;
			else if (friendlyName.equalsIgnoreCase("Punch") || friendlyName.equalsIgnoreCase("Push"))
				return Enchantment.ARROW_KNOCKBACK;
			else if (friendlyName.equalsIgnoreCase("Efficiency") || friendlyName.equalsIgnoreCase("Eff"))
				return Enchantment.DIG_SPEED;
			else if (friendlyName.equalsIgnoreCase("Unbreaking") || friendlyName.equalsIgnoreCase("Durability") || friendlyName.equalsIgnoreCase("Dura"))
				return Enchantment.DURABILITY;
			else if (friendlyName.equalsIgnoreCase("Fire Aspect") || friendlyName.equalsIgnoreCase("Fire"))
				return Enchantment.FIRE_ASPECT;
			else if (friendlyName.equalsIgnoreCase("Knockback") || friendlyName.equalsIgnoreCase("Knock"))
				return Enchantment.KNOCKBACK;
			else if (friendlyName.equalsIgnoreCase("Fortune") || friendlyName.equalsIgnoreCase("Fort"))
				return Enchantment.LOOT_BONUS_BLOCKS;
			else if (friendlyName.equalsIgnoreCase("Looting") || friendlyName.equalsIgnoreCase("Loot"))
				return Enchantment.LOOT_BONUS_MOBS;
			else if (friendlyName.equalsIgnoreCase("Luck"))
				return Enchantment.LUCK;
			else if (friendlyName.equalsIgnoreCase("Lure"))
				return Enchantment.LURE;
			else if (friendlyName.equalsIgnoreCase("Oxygen") || friendlyName.equalsIgnoreCase("Breathing") || friendlyName.equalsIgnoreCase("Respiration"))
				return Enchantment.OXYGEN;
			else if (friendlyName.equalsIgnoreCase("Protection") || friendlyName.equalsIgnoreCase("Prot"))
				return Enchantment.PROTECTION_ENVIRONMENTAL;
			else if (friendlyName.equalsIgnoreCase("Blast Protection") || friendlyName.equalsIgnoreCase("BlastProt"))
				return Enchantment.PROTECTION_EXPLOSIONS;
			else if (friendlyName.equalsIgnoreCase("Fall Protection") || friendlyName.equalsIgnoreCase("FallProt") || friendlyName.equalsIgnoreCase("Feather") || friendlyName.equalsIgnoreCase("Feather Falling"))
				return Enchantment.PROTECTION_FALL;
			else if (friendlyName.equalsIgnoreCase("Fire Protection") || friendlyName.equalsIgnoreCase("FireProt"))
				return Enchantment.PROTECTION_FIRE;
			else if (friendlyName.equalsIgnoreCase("Projectile Protection") || friendlyName.equalsIgnoreCase("ProjProt"))
				return Enchantment.PROTECTION_PROJECTILE;
			else if (friendlyName.equalsIgnoreCase("Silk Touch") || friendlyName.equalsIgnoreCase("SilkTouch") || friendlyName.equalsIgnoreCase("Silk"))
				return Enchantment.SILK_TOUCH;
			else if (friendlyName.equalsIgnoreCase("Thorns"))
				return Enchantment.THORNS;
			else if (friendlyName.equalsIgnoreCase("Water Worker") || friendlyName.equalsIgnoreCase("Aqua Affinity"))
				return Enchantment.WATER_WORKER;
		}
		return null;
	}

}
