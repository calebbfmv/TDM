package org.calebbfmv.util.barapi;

import org.bukkit.*;
import org.bukkit.craftbukkit.v1_7_R4.entity.*;
import org.bukkit.entity.*;
import org.calebbfmv.util.barapi.nms.*;

import java.lang.reflect.*;

/**
 * This is a utility class for BarAPI. It is based on the code by SoThatsIt.
 * <p>
 * http://forums.bukkit.org/threads/tutorial-utilizing-the-boss-health-bar
 * .158018/page-2#post-1760928
 *
 * @author James Mortemore
 */
public class BarUtil {
    public static boolean newProtocol = false;
    public static String version;
    public static Class<?> fakeDragonClass = v1_7.class;
    public static Class<?> new_fakeDragonClass = v1_8.class;

    static {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String mcVersion = name.substring(name.lastIndexOf('.') + 1);
        String[] versions = mcVersion.split("_");

        if (versions[0].equals("v1") && Integer.parseInt(versions[1]) == 7) {
            newProtocol = true;
            // fakeDragonClass = v1_7.class;
        }

        version = mcVersion + ".";
    }

    public static FakeWither newWither(String message, Location loc) {
        FakeWither fakeWither = null;

        try {
            fakeWither = (FakeWither) new_fakeDragonClass.getConstructor(String.class, Location.class).newInstance(message, loc);
        } catch (IllegalArgumentException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException | SecurityException e) {
            e.printStackTrace();
        }

        return fakeWither;

    }

    public static FakeDragon newDragon(String message, Location loc) {
        FakeDragon fakeDragon = null;
        try {
            fakeDragon = (FakeDragon) fakeDragonClass.getConstructor(String.class, Location.class).newInstance(message, loc);
        } catch (IllegalArgumentException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException | SecurityException e) {
            e.printStackTrace();
        }

        return fakeDragon;
    }

    // Reflection Util
    public static void sendPacket(Player p, Object packet) {
        try {
            Object nmsPlayer = getHandle(p);
            Field con_field = nmsPlayer.getClass().getField("playerConnection");
            Object con = con_field.get(nmsPlayer);
            Method packet_method = getMethod(con.getClass(), "sendPacket");
            packet_method.invoke(con, packet);
        } catch (SecurityException | NoSuchFieldException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getCraftClass(String ClassName) {
        String className = "net.minecraft.server." + version + ClassName;
        Class<?> c = null;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Object getHandle(World world) {
        Object nms_entity = null;
        Method entity_getHandle = getMethod(world.getClass(), "getHandle");
        try {
            nms_entity = entity_getHandle.invoke(world);
        } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return nms_entity;
    }

    public static Object getHandle(Entity entity) {
        Object nms_entity = null;
        Method entity_getHandle = getMethod(entity.getClass(), "getHandle");
        try {
            nms_entity = entity_getHandle.invoke(entity);
        } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return nms_entity;
    }

    public static Field getField(Class<?> cl, String field_name) {
        try {
            Field field = cl.getDeclaredField(field_name);
            return field;
        } catch (SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethod(Class<?> cl, String method, Class<?>[] args) {
        for (Method m : cl.getMethods()) {
            if (m.getName().equals(method) && ClassListEqual(args, m.getParameterTypes())) {
                return m;
            }
        }
        return null;
    }

    public static Method getMethod(Class<?> cl, String method, Integer args) {
        for (Method m : cl.getMethods()) {
            if (m.getName().equals(method) && args.equals(new Integer(m.getParameterTypes().length))) {
                return m;
            }
        }
        return null;
    }

    public static Method getMethod(Class<?> cl, String method) {
        for (Method m : cl.getMethods()) {
            if (m.getName().equals(method)) {
                return m;
            }
        }
        return null;
    }

    public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;

        if (l1.length != l2.length) {
            return false;
        }
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        }

        return equal;
    }

    public static boolean hasNewProtocol(Player player) {
        return ((CraftPlayer) player).getHandle().
                playerConnection.networkManager.getVersion() > 5;
    }
}