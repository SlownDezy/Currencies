package io.github.slowndezy.currencies.command.subcommands;

import io.github.slowndezy.currencies.CurrenciesPlugin;
import io.github.slowndezy.currencies.command.MoneyCommand;
import io.github.slowndezy.currencies.config.MessagesConfig;
import io.github.slowndezy.currencies.entities.Account;
import io.github.slowndezy.currencies.events.MoneyUpdateEvent;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddCommand extends MoneyCommand {

    public AddCommand(CurrenciesPlugin plugin) {
        super(plugin);
    }

    @Command(name = "money.add", aliases = { "adicionar" }, permission = "currencies.admin")
    public void addCommand(Context<CommandSender> sender, Player target, int amount) {
        Account targetAccount = services.findByName(target.getName());

        if (targetAccount == null) {
            sender.sendMessage(MessagesConfig.get(MessagesConfig::incorrectTagert));
            return;
        }

        if (amount <= 0) {
            sender.sendMessage(MessagesConfig.get(MessagesConfig::incorrectValue));
            return;
        }


        MoneyUpdateEvent moneyUpdateEvent = new MoneyUpdateEvent(target, MoneyUpdateEvent.UpdateType.ADD, amount);
        Bukkit.getPluginManager().callEvent(moneyUpdateEvent);

        if (!moneyUpdateEvent.isCancelled()) {
            targetAccount.deposit(amount);
            sender.sendMessage(MessagesConfig.get(MessagesConfig::moneyAddCommand)
                    .replace("{player}", targetAccount.getName())
                    .replace("{coins}", String.valueOf(amount)));
        }
    }
}