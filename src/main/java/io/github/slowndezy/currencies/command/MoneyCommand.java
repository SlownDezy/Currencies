package io.github.slowndezy.currencies.command;

import io.github.slowndezy.currencies.CurrenciesPlugin;
import io.github.slowndezy.currencies.config.MessagesConfig;
import io.github.slowndezy.currencies.entities.Account;
import io.github.slowndezy.currencies.entities.services.AccountServices;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.entity.Player;

public class MoneyCommand {

    public final AccountServices services;

    public MoneyCommand(CurrenciesPlugin plugin) {
        this.services = plugin.getAccountServices();
    }

    @Command(name = "money", aliases = { "coins", "moedas"})
    public void moneyCommand(Context<Player> player, @Optional Player target) {
        Account account = services.findByName(player.getSender().getName());

        if (target != null) {
            account = services.findByName(target.getName());
        }

        player.sendMessage(MessagesConfig.get(MessagesConfig::moneyCommand)
                .replace("{player}", account.getName())
                .replace("{coins}", account.getFormatedBalance()));
    }
}
