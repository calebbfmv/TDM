package org.calebbfmv.util.barapi;

import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.calebbfmv.*;
import org.calebbfmv.util.barapi.nms.*;

import java.util.*;

/**
 * Allows plugins to safely set a health bar message.
 *
 * @author James Mortemore
 */

public class BarAPI implements Listener {

    private static Map<UUID, FakeDragon> dragon_players = new HashMap<>();
    private static Map<UUID, FakeWither> wither_players = new HashMap<>();
    private static Map<UUID, Integer> timers = new HashMap<>();

    private static TDM plugin;

    public BarAPI() {
        onEnable();
        plugin = TDM.getInstance();
    }

    public void onEnable() {
        TDM.getInstance().getServer().getPluginManager().registerEvents(this, TDM.getInstance());
        TDM.getInstance().getLogger().info("Loaded");
    }

    public void onDisable() {
        plugin.getServer().getOnlinePlayers().forEach(this::quit);
        dragon_players.clear();
        wither_players.clear();
        for (int timerID : timers.values()) {
            Bukkit.getScheduler().cancelTask(timerID);
        }
        timers.clear();
    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void PlayerLoggout(PlayerQuitEvent event) {
        quit(event.getPlayer());
    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerKick(PlayerKickEvent event) {
        quit(event.getPlayer());
    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        handleTeleport(event.getPlayer(), event.getTo().clone());
    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(final PlayerRespawnEvent event) {
        handleTeleport(event.getPlayer(), event.getRespawnLocation().clone());
    }

    private void handleTeleport(final Player player, final Location loc) {

        if (! hasDragonBar(player) && ! hasWitherBar(player)) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

            @Override
            public void run() {
                // Check if the player still has a dragon after the two ticks! ;)
                if (! hasDragonBar(player) && ! hasWitherBar(player)) {
                    return;
                }

                if (dragon_players.containsKey(player.getUniqueId())) {
                    FakeDragon oldDragon = getDragon(player, "");

                    float health = oldDragon.health;
                    String message = oldDragon.name;

                    BarUtil.sendPacket(player, getDragon(player, "").getDestroyPacket());

                    dragon_players.remove(player.getUniqueId());

                    FakeDragon dragon = addDragon(player, loc, message);
                    dragon.health = health;

                    sendDragon(dragon, player);
                } else if (wither_players.containsKey(player.getUniqueId())) {
                    FakeWither oldWither = getWither(player, "");

                    float health = oldWither.health;
                    String message = oldWither.name;

                    BarUtil.sendPacket(player, getWither(player, "").getDestroyPacket());

                    dragon_players.remove(player.getUniqueId());

                    FakeWither wither = addWither(player, loc, message);
                    wither.health = health;

                    sendWither(wither, player);
                }
            }

        }, 2L);
    }

    private void quit(Player player) {
        removeBar(player);
    }

    public static void setMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setMessage(player, message);
        }
    }

    public static void setMessage(Player player, String message) {
        if (BarUtil.hasNewProtocol(player)) {
            FakeWither wither = getWither(player, message);

            wither.name = cleanMessage(message);
            wither.health = FakeWither.MAX_HEALTH;

            cancelTimer(player);

            sendWither(wither, player);
        } else {
            FakeDragon dragon = getDragon(player, message);

            dragon.name = cleanMessage(message);
            dragon.health = FakeDragon.MAX_HEALTH;

            cancelTimer(player);

            sendDragon(dragon, player);
        }
    }


    public static void setMessage(String message, float percent) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setMessage(player, message, percent);
        }
    }

    public static void setMessage(Player player, String message, float percent) {
        Validate.isTrue(0F <= percent && percent <= 100F, "Percent must be between 0F and 100F, but was: ", percent);

        if (BarUtil.hasNewProtocol(player)) {
            FakeWither wither = getWither(player, message);

            wither.name = cleanMessage(message);
            wither.health = (percent / 100f) * FakeWither.MAX_HEALTH;

            cancelTimer(player);

            sendWither(wither, player);
        } else {
            FakeDragon dragon = getDragon(player, message);

            dragon.name = cleanMessage(message);
            dragon.health = (percent / 100f) * FakeDragon.MAX_HEALTH;

            cancelTimer(player);

            sendDragon(dragon, player);
        }
    }


    public static void setMessage(String message, int seconds) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setMessage(player, message, seconds);
        }
    }

    public static void setMessage(final Player player, String message, int seconds) {
        Validate.isTrue(seconds > 0, "Seconds must be above 1 but was: ", seconds);

        if (BarUtil.hasNewProtocol(player)) {
            FakeWither wither = getWither(player, message);

            wither.name = cleanMessage(message);
            wither.health = FakeWither.MAX_HEALTH;

            final float witherHealthMinus = FakeWither.MAX_HEALTH / seconds;

            cancelTimer(player);

            timers.put(player.getUniqueId(), Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

                @Override
                public void run() {
                    FakeWither with = getWither(player, "");
                    with.health -= witherHealthMinus;

                    if (with.health <= 1) {
                        removeBar(player);
                        cancelTimer(player);
                    } else {
                        sendWither(with, player);
                    }
                }

            }, 20L, 20L).getTaskId());

            sendWither(wither, player);
        } else {
            FakeDragon dragon = getDragon(player, message);

            dragon.name = cleanMessage(message);
            dragon.health = FakeDragon.MAX_HEALTH;

            final float dragonHealthMinus = FakeDragon.MAX_HEALTH / seconds;

            cancelTimer(player);

            timers.put(player.getUniqueId(), Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

                @Override
                public void run() {
                    FakeDragon drag = getDragon(player, "");
                    drag.health -= dragonHealthMinus;

                    if (drag.health <= 1) {
                        removeBar(player);
                        cancelTimer(player);
                    } else {
                        sendDragon(drag, player);
                    }
                }

            }, 20L, 20L).getTaskId());

            sendDragon(dragon, player);
        }
    }

    public static boolean hasDragonBar(Player player) {
        return dragon_players.get(player.getUniqueId()) != null;
    }

    public static boolean hasWitherBar(Player player) {
        return wither_players.get(player.getUniqueId()) != null;
    }

    public static void removeBar(Player player) {
        if (! hasDragonBar(player) && ! hasWitherBar(player)) {
            return;
        }

        if (hasDragonBar(player)) {
            BarUtil.sendPacket(player, getDragon(player, "").getDestroyPacket());
            dragon_players.remove(player.getUniqueId());
            cancelTimer(player);
        } else if (hasWitherBar(player)) {
            BarUtil.sendPacket(player, getWither(player, "").getDestroyPacket());
            wither_players.remove(player.getUniqueId());
            cancelTimer(player);
        }
    }

    public static void setHealth(Player player, float percent) {
        if (! hasDragonBar(player) && ! hasWitherBar(player)) {
            return;
        }

        if (hasDragonBar(player)) {
            FakeDragon dragon = getDragon(player, "");
            dragon.health = (percent / 100f) * FakeDragon.MAX_HEALTH;

            cancelTimer(player);

            if (percent == 0) {
                removeBar(player);
            } else {
                sendDragon(dragon, player);
            }
        } else if (hasWitherBar(player)) {
            FakeWither wither = getWither(player, "");
            wither.health = (percent / 100f) * FakeWither.MAX_HEALTH;

            cancelTimer(player);

            if (percent == 0) {
                removeBar(player);
            } else {
                sendWither(wither, player);
            }
        }
    }

    public static float getHealth(Player player) {
        if (! hasDragonBar(player) && ! hasWitherBar(player)) {
            return - 1;
        }

        if (hasDragonBar(player)) {
            return getDragon(player, "").health;
        } else if (hasWitherBar(player)) {
            return getWither(player, "").health;
        }

        return - 1;
    }

    public static String getMessage(Player player) {
        if (! hasDragonBar(player) && ! hasWitherBar(player)) {
            return "";
        }

        if (hasDragonBar(player)) {
            return getDragon(player, "").name;
        } else if (hasWitherBar(player)) {
            return getWither(player, "").name;
        }

        return "";
    }

    private static String cleanMessage(String message) {
        if (message.length() > 64) {
            message = message.substring(0, 63);
        }

        return message;
    }

    private static void cancelTimer(Player player) {
        Integer timerID = timers.remove(player.getUniqueId());

        if (timerID != null) {
            Bukkit.getScheduler().cancelTask(timerID);
        }
    }

    private static void sendDragon(FakeDragon dragon, Player player) {
        BarUtil.sendPacket(player, dragon.getMetaPacket(dragon.getWatcher()));

        if (BarUtil.hasNewProtocol(player)) {
            BarUtil.sendPacket(player, dragon.getTeleportPacket(player.getLocation().add(player.getEyeLocation().getDirection().multiply(20))));
        } else {
            BarUtil.sendPacket(player, dragon.getTeleportPacket(player.getLocation().add(0, -300, 0)));
        }
    }

    private static void sendWither(FakeWither wither, Player player) {
        BarUtil.sendPacket(player, wither.getMetaPacket(wither.getWatcher()));

        if (BarUtil.hasNewProtocol(player)) {
            BarUtil.sendPacket(player, wither.getTeleportPacket(player.getLocation().add(player.getEyeLocation().getDirection().multiply(20))));
        } else {
            BarUtil.sendPacket(player, wither.getTeleportPacket(player.getLocation().add(0, -300, 0)));
        }
    }

    private static FakeDragon getDragon(Player player, String message) {
        if (dragon_players.containsKey(player.getUniqueId())) {
            return dragon_players.get(player.getUniqueId());
        } else {
            return addDragon(player, cleanMessage(message));
        }
    }

    private static FakeWither getWither(Player player, String message) {
        if (wither_players.containsKey(player.getUniqueId())) {
            return wither_players.get(player.getUniqueId());
        } else {
            return addWither(player, cleanMessage(message));
        }
    }

    private static FakeWither addWither(Player player, String message) {
        FakeWither wither;
        wither = BarUtil.newWither(message, player.getLocation().add(player.getEyeLocation().getDirection().multiply(20)));
        BarUtil.sendPacket(player, wither.getSpawnPacket());
        wither_players.put(player.getUniqueId(), wither);
        return wither;
    }

    private static FakeWither addWither(Player player, Location loc, String message) {
        FakeWither wither;
        // loc.add ?
        wither = BarUtil.newWither(message, player.getLocation().add(player.getEyeLocation().getDirection().multiply(20)));
        BarUtil.sendPacket(player, wither.getSpawnPacket());
        wither_players.put(player.getUniqueId(), wither);
        return wither;
    }

    private static FakeDragon addDragon(Player player, String message) {
        boolean ver_1_8 = BarUtil.hasNewProtocol(player);
        FakeDragon dragon = null;
        FakeWither wither;

        if (ver_1_8) {
            wither = BarUtil.newWither(message, player.getLocation().add(player.getEyeLocation().getDirection().multiply(20)));
            BarUtil.sendPacket(player, wither.getSpawnPacket());
            wither_players.put(player.getUniqueId(), wither);
        } else {
            dragon = BarUtil.newDragon(message, player.getLocation().add(0, -300, 0));
            BarUtil.sendPacket(player, dragon.getSpawnPacket());
            dragon_players.put(player.getUniqueId(), dragon);
        }

        return dragon;
    }

    private static FakeDragon addDragon(Player player, Location loc, String message) {
        boolean ver_1_8 = BarUtil.hasNewProtocol(player);
        FakeDragon dragon = null;
        FakeWither wither = null;

        if (ver_1_8) {
            wither = BarUtil.newWither(message, player.getLocation().add(player.getEyeLocation().getDirection().multiply(20)));
            BarUtil.sendPacket(player, wither.getSpawnPacket());
            wither_players.put(player.getUniqueId(), wither);
        } else {
            dragon = BarUtil.newDragon(message, player.getLocation().add(0, -300, 0));
            BarUtil.sendPacket(player, dragon.getSpawnPacket());
            dragon_players.put(player.getUniqueId(), dragon);
        }

        return dragon;
    }
}
