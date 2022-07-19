package net.vorium.currencies.command.subcommands;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import net.vorium.currencies.Main;
import net.vorium.currencies.command.MoneyCommand;
import net.vorium.currencies.entities.Account;
import org.bukkit.command.CommandSender;

public class AddCommand extends MoneyCommand {

    public AddCommand(Main plugin) {
        super(plugin);
    }

    @Command(name = "money.add", aliases = { "adicionar" }, usage = "<player> <quantia>", permission = "currencies.admin")
    public void addCommand(Context<CommandSender> sender, String targetName, int amount) {
        Account targetAccount = services.findByName(targetName);

        if (targetAccount == null) {
            sender.sendMessage("§cNão foi possível encontrar este jogador.");
            return;
        }

        if (amount <= 0) {
            sender.sendMessage("§cInsira um número válido.");
            return;
        }

        targetAccount.deposit(amount);

        sender.sendMessage("§eVocê adicionou §a" + format.format(amount) + " §ecoins na conta de " + targetAccount.getPrefix() + targetAccount.getName());
    }
}
