package net.vorium.currencies.command.subcommands;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import net.vorium.currencies.Main;
import net.vorium.currencies.command.MoneyCommand;
import net.vorium.currencies.entities.Account;
import net.vorium.currencies.events.MoneyUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveCommand extends MoneyCommand {

    public RemoveCommand(Main plugin) {
        super(plugin);
    }

    @Command(name = "money.withdraw", aliases = { "remover", "remove" }, permission = "currencies.admin")
    public void Command(Context<CommandSender> sender, Player target, int amount) {
        Account targetAccount = services.findByName(target.getName());

        if (targetAccount == null) {
            sender.sendMessage("§cNão foi possível encontrar este jogador.");
            return;
        }

        if (amount <= 0) {
            sender.sendMessage("§cInsira um número válido.");
            return;
        }
        targetAccount.withdraw(amount);
        Bukkit.getPluginManager().callEvent(new MoneyUpdateEvent(target, MoneyUpdateEvent.UpdateType.SET, amount));
        sender.sendMessage("§eVocê removeu §a" + format.format(amount) + " §ecoins da conta de " + targetAccount.getPrefix() + targetAccount.getName());

    }
}
