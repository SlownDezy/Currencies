package net.vorium.currencies.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SerializeLocation {

    public static String toSerialize(Location location) {
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        return world
                + "@" + x
                + "@" + y
                + "@" + z
                + "@" + yaw
                + "@" + pitch;
    }

    public static Location fromSerialized(String serialized) {
        String[] parts = serialized.split("@");

        World world = Bukkit.getServer().getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }
}
