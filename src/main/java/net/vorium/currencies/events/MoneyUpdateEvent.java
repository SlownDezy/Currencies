package net.vorium.currencies.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MoneyUpdateEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();

    private Player player;
    private final UpdateType type;
    private final double amount;
    private final Player target;

    private boolean isCancelled;

    public MoneyUpdateEvent(Player target, UpdateType type, double amount) {
        this.target = target;
        this.type = type;
        this.amount = amount;
        this.isCancelled = false;
    }

    public MoneyUpdateEvent(Player player, Player target, UpdateType type, double amount) {
        this.player = player;
        this.target = target;
        this.type = type;
        this.amount = amount;
        this.isCancelled = false;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
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
