package net.vorium.currencies.entities.services;

import net.vorium.currencies.Main;
import net.vorium.currencies.entities.Account;
import net.vorium.currencies.entities.RankingNPC;
import net.vorium.currencies.utils.SerializeLocation;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class RankingServices {

    private final FileConfiguration configuration;

    private final List<RankingNPC> npcs = new ArrayList<>();
    private final List<Account> accounts;

    public RankingServices(Main plugin) {
        configuration = plugin.getConfig();
        accounts = plugin.getAccountServices().getRanking(10);
    }

    public void create(int position, Location location) {
        RankingNPC npc = new RankingNPC(
                position,
                location,
                accounts.get(position));
        npcs.add(npc);
        save(npc);
    }

    public void load() {
        ConfigurationSection worldSection = configuration.getConfigurationSection("rankings");
        for (String world : worldSection.getKeys(false)) {
            ConfigurationSection positionSection = worldSection.getConfigurationSection(world);
            for (String position : positionSection.getKeys(false)) {
                int rank = Integer.parseInt(position);
                ConfigurationSection section = positionSection.getConfigurationSection(position);

                if (accounts.get(rank) == null) return;

                npcs.add(new RankingNPC(
                        rank,
                        SerializeLocation.fromSerialized(
                                section,
                                rank),
                        accounts.get(rank)
                        ));
            }
        }
    }

    public void unload() {
        for (RankingNPC npc : npcs) {
            save(npc);
            npc.getHologram().despawn();
            npcs.remove(npc);
        }
    }

    public void update() {
        for (RankingNPC npc : npcs) {
            npc.update();
        }
    }

    public void save(RankingNPC npc) {
        SerializeLocation.toSerialize(
                configuration,
                npc.getPosition(),
                npc.getLocation());
    }
}
