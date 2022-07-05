package net.vorium.currencies.command.subcommands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import net.vorium.currencies.Main;
import net.vorium.currencies.command.MoneyCommand;
import net.vorium.currencies.entities.Account;
import org.bukkit.command.CommandSender;

public class RemoveCommand extends MoneyCommand {

    public RemoveCommand(Main plugin) {
        super(plugin);
    }

    @Command(name = "withdraw", aliases = { "remover", "remove" }, desc = "Remova moedas de um jogador.", usage = "<player> <quantia>")
    @Require("currencies.admin")
    public void Command(@Sender CommandSender sender, String targetName, int amount) {
        Account targetAccount = services.findByName(targetName);

        if (targetAccount == null) {
            sender.sendMessage("§cNão foi possível encontrar este jogador.");
            return;
        }

        if (amount <= 0) {
            sender.sendMessage("§cInsira um número válido.");
            return;
        }
        targetAccount.withdraw(amount);

        sender.sendMessage("§eVocê removeu §a" + format.format(amount) + " §ecoins da conta de " + targetAccount.getPrefix() + targetAccount.getName());

    }
}
