package net.vorium.currencies.command;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import net.vorium.currencies.Main;
import net.vorium.currencies.entities.Account;
import net.vorium.currencies.entities.services.AccountServices;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class MoneyCommand {

    public final AccountServices services;

    public final DecimalFormat format = new DecimalFormat("#,###.#");

    public MoneyCommand(Main plugin) {
        this.services = plugin.getAccountServices();
    }

    @Command(name = "money", aliases = { "coins", "moedas"})
    public void moneyCommand(Context<Player> player, @Optional Player target) {
        Account account = services.findByName(player.getSender().getName());

        if (target != null) {
            account = services.findByName(target.getName());
        }

         player.sendMessage((target == null ? "§eVocê" : account.getPrefix() + account.getName()) + " §epossui: §a" + account.getFormatedBalance() + " coins");
    }
}
