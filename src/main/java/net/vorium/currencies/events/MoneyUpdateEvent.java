package net.vorium.currencies.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MoneyUpdateEvent extends Event implements Cancellable {

    private Player player;
    private final UpdateType type;
    private final double amount;
    private final Player target;

    public MoneyUpdateEvent(Player target, UpdateType type, double amount) {
        this.target = target;
        this.type = type;
        this.amount = amount;
    }

    public MoneyUpdateEvent(Player player, Player target, UpdateType type, double amount) {
        this.player = player;
        this.target = target;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    public Player getPlayer() {
        return this.player;
    }

    public UpdateType getType() {
        return this.type;
    }

    public double getAmount() {
        return this.amount;
    }

    public Player getTarget() {
        return this.target;
    }

    public enum UpdateType {
        SET,
        PAY,
        WITHDRAW;
    }
}
