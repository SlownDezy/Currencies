package net.vorium.currencies.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import me.lucko.helper.hologram.Hologram;
import me.lucko.helper.npc.CitizensNpcFactory;
import me.lucko.helper.npc.Npc;
import me.lucko.helper.serialize.Position;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.util.Arrays;

@Getter
public class RankingNPC {

    private final int position;
    private final Location location;
    private final Account account;
    private Hologram hologram;
    private Npc npc;

    public RankingNPC(int position, Location location, Account account) {
        this.position = position;
        this.location = location;
        this.account = account;
        setupNPC();
        setupHologram();
    }

    public void setupHologram() {
        if (hologram != null) hologram.despawn();

        hologram = Hologram.create(
                Position.of(location.clone().add(0, 3.5, 0)),
                Arrays.asList(
                        "§b" + position + "º Lugar",
                        account.getPrefix() + account.getPrefix(),
                        "§a" + account.getFormatedBalance() + " coins"
                ));
    }

    public void setupNPC() {
        npc = new CitizensNpcFactory()
                .spawnNpc(
                        location,
                        "MONEY_RANKING_" + position,
                        getPlayerSkin(account.getName())[0]);
        npc.setShowNametag(false);
    }

    public void update() {
        hologram.updateLines(Arrays.asList(
                "§b" + position + "º Lugar",
                account.getPrefix() + account.getPrefix(),
                "§a" + account.getFormatedBalance() + " coins"));
        npc.setSkin(getPlayerSkin(account.getName())[0], getPlayerSkin(account.getName())[1]);
    }

    private String[] getPlayerSkin(String playerName) {
        EntityPlayer entityPlayer = ((CraftPlayer) Bukkit.getPlayer(playerName)).getHandle();
        GameProfile profile = entityPlayer.getProfile();
        Property property = profile.getProperties().get("textrures").iterator().next();
        return new String[] { property.getValue(), property.getSignature() };
    }
}

