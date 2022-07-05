package net.vorium.currencies.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class SerializeLocation {

    public static void toSerialize(FileConfiguration configuration, int position, Location location) {
        ConfigurationSection section = configuration.getConfigurationSection(
                location.getWorld().getName());
        section.set(position + ".x", location.getX());
        section.set(position + ".y", location.getY());
        section.set(position + ".z", location.getZ());
        section.set(position + ".yaw", location.getYaw());
        section.set(position + ".pitch", location.getPitch());

    }

    public static Location fromSerialized(ConfigurationSection section, int position) {
        World world = Bukkit.getWorld(section.getName());
        double x = section.getDouble(position + ".x");
        double y = section.getDouble(position + ".y");
        double z = section.getDouble(position + ".z");
        float yaw = (float) section.getDouble(position + ".yaw");
        float pitch = (float) section.getDouble(position + ".pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }
}
