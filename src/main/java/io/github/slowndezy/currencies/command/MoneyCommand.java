package io.github.slowndezy.currencies.command;

import io.github.slowndezy.currencies.entities.Account;
import io.github.slowndezy.currencies.entities.services.AccountServices;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import io.github.slowndezy.currencies.CurrenciesPlugin;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class MoneyCommand {

    public final AccountServices services;

    public final DecimalFormat format = new DecimalFormat("#,###.#");

    public MoneyCommand(CurrenciesPlugin plugin) {
        this.services = plugin.getAccountServices();
    }

    @Command(name = "money", aliases = { "coins", "moedas"})
    public void moneyCommand(Context<Player> player, @Optional Player target) {
        Account account = services.findByName(player.getSender().getName());

        if (target != null) {
            account = services.findByName(target.getName());
        }

         player.sendMessage((target == null ? "§eVocê" : account.getName()) + " §epossui: §a" + account.getFormatedBalance() + " coins");
    }
}
