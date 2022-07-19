package net.vorium.currencies.command.subcommands;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.vorium.currencies.Main;
import net.vorium.currencies.command.MoneyCommand;
import net.vorium.currencies.entities.Account;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PayCommand extends MoneyCommand {

    public PayCommand(Main plugin) {
        super(plugin);
    }

    @Command(name = "money.pay", aliases = { "enviar", "pagar" }, usage = "<player> <quantia>", target = CommandTarget.PLAYER)
    public void payCommand(Context<Player> player, String targetName, int amount) {
        Account account = services.findByName(player.getSender().getName());
        if (account == null) return;

        Account targetAccount = services.findByName(targetName);
        if (targetAccount == null) return;

        if (account.equals(targetAccount)) {
            player.sendMessage("§cVocê não pode enviar moedas para si mesmo.");
            return;
        }

        if (amount <= 0) {
            player.sendMessage("§cInsira um número válido.");
            return;
        }

        account.withdraw(amount);
        targetAccount.deposit(amount);

        player.sendMessage("§eVocê enviou §a" + format.format(amount) + " §ecoins para " + targetAccount.getPrefix() + targetAccount.getName());

        Player target = Bukkit.getPlayer(targetName);
        if (target.isOnline()) target.sendMessage("§eVocê recebeu §a" + format.format(amount) + " §ecoins de " + account.getPrefix() + account.getName());

    }
}
