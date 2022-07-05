package net.vorium.currencies.entities;

import lombok.Getter;
import me.lucko.helper.hologram.Hologram;
import me.lucko.helper.npc.CitizensNpcFactory;
import me.lucko.helper.npc.Npc;
import me.lucko.helper.serialize.Position;
import org.bukkit.Location;

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
                        account.getPrefix() + account.getName(),
                        "§a" + account.getFormatedBalance() + " coins"
                ));
    }

    public void setupNPC() {
        npc = new CitizensNpcFactory().spawnNpc(location, "MONEY_RANKING_" + position, "");
        npc.setShowNametag(false);
    }
}

