package io.github.slowndezy.currencies.command.subcommands;

import io.github.slowndezy.currencies.entities.Account;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import io.github.slowndezy.currencies.CurrenciesPlugin;
import io.github.slowndezy.currencies.command.MoneyCommand;
import io.github.slowndezy.currencies.events.MoneyUpdateEvent;
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
        if (targetAccount == null) return;

        if (account.equals(targetAccount)) {
            player.sendMessage("§cVocê não pode enviar moedas para si mesmo.");
            return;
        }

        if (amount <= 0) {
            player.sendMessage("§cInsira um número válido.");
            return;
        }

        MoneyUpdateEvent moneyUpdateEvent = new MoneyUpdateEvent(target, MoneyUpdateEvent.UpdateType.PAY, amount);
        Bukkit.getPluginManager().callEvent(moneyUpdateEvent);

        if (!moneyUpdateEvent.isCancelled()) {
            account.withdraw(amount);
            targetAccount.deposit(amount);

            player.sendMessage("§eVocê enviou §a" + format.format(amount) + " §ecoins para " + targetAccount.getName());
            if (target.isOnline()) target.sendMessage("§eVocê recebeu §a" + format.format(amount) + " §ecoins de " + account.getName());
        }
    }
}
