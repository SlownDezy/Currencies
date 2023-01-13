package io.github.slowndezy.currencies.command.subcommands;

import io.github.slowndezy.currencies.CurrenciesPlugin;
import io.github.slowndezy.currencies.command.MoneyCommand;
import io.github.slowndezy.currencies.config.MessagesConfig;
import io.github.slowndezy.currencies.entities.Account;
import io.github.slowndezy.currencies.events.MoneyUpdateEvent;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PayCommand extends MoneyCommand {

    public PayCommand(CurrenciesPlugin plugin) {
        super(plugin);
    }

    @Command(name = "money.pay", aliases = { "enviar", "pagar" }, target = CommandTarget.PLAYER)
    public void payCommand(Context<Player> player, Player target, int amount) {
        Account account = services.findByName(player.getSender().getName());
        if (account == null) return;

        Account targetAccount = services.findByName(target.getName());
        if (targetAccount == null) {
            player.sendMessage(MessagesConfig.get(MessagesConfig::incorrectTagert));
            return;
        }

        if (account.equals(targetAccount)) {
            player.sendMessage(MessagesConfig.get(MessagesConfig::yourselfError));
            return;
        }

        if (amount <= 0) {
            player.sendMessage(MessagesConfig.get(MessagesConfig::incorrectValue));
            return;
        }

        if (account.getBalance() <= amount) {
            player.sendMessage(MessagesConfig.get(MessagesConfig::insufficientAmount));
            return;
        }

        MoneyUpdateEvent moneyUpdateEvent = new MoneyUpdateEvent(target, MoneyUpdateEvent.UpdateType.PAY, amount);
        Bukkit.getPluginManager().callEvent(moneyUpdateEvent);

        if (!moneyUpdateEvent.isCancelled()) {
            account.withdraw(amount);
            targetAccount.deposit(amount);

            player.sendMessage(MessagesConfig.get(MessagesConfig::paid)
                    .replace("{player}", targetAccount.getName())
                    .replace("{coins}", String.valueOf(amount)));
            if (target.isOnline()) target.sendMessage(MessagesConfig.get(MessagesConfig::received)
                    .replace("{player}", targetAccount.getName())
                    .replace("{coins}", String.valueOf(amount)));
        }
    }
}
