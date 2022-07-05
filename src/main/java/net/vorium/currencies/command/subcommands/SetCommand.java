package net.vorium.currencies.command.subcommands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import net.vorium.currencies.Main;
import net.vorium.currencies.command.MoneyCommand;
import net.vorium.currencies.entities.Account;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand extends MoneyCommand {

    public SetCommand(Main plugin) {
        super(plugin);
    }

    @Command(name = "set", aliases = { "setar", "definir" }, desc = "Defina quantas moedas um jogador possui.", usage = "<player> <quantia>")
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

        targetAccount.set(amount);

        sender.sendMessage("§eVocê definiu §a" + format.format(amount) + " §ecoins para " + targetAccount.getPrefix() + targetAccount.getName());
    }
}
