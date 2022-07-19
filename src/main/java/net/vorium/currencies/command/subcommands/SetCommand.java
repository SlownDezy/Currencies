package net.vorium.currencies.command.subcommands;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import net.vorium.currencies.Main;
import net.vorium.currencies.command.MoneyCommand;
import net.vorium.currencies.entities.Account;
import org.bukkit.command.CommandSender;

public class SetCommand extends MoneyCommand {

    public SetCommand(Main plugin) {
        super(plugin);
    }

    @Command(name = "money.set", aliases = { "setar", "definir" }, usage = "<player> <quantia>", permission = "currencies.admin")
    public void Command(Context<CommandSender> sender, String targetName, int amount) {
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
