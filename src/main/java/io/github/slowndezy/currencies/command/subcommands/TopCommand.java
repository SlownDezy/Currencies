package io.github.slowndezy.currencies.command.subcommands;

import io.github.slowndezy.currencies.entities.Account;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import io.github.slowndezy.currencies.CurrenciesPlugin;
import io.github.slowndezy.currencies.command.MoneyCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class TopCommand extends MoneyCommand {

    public TopCommand(CurrenciesPlugin plugin) {
        super(plugin);
    }

    @Command(name = "money.top", aliases = "ranking")
    public void topCommand(Context<CommandSender> sender) {
        sender.sendMessage( new String[] { "", "§2 Top 10 jogadores mais ricos", ""});

        int rank = 1;
        for (Account account : services.getRanking(10)) {
            sender.sendMessage("§f  " + rank + ". §7"
                    + Bukkit.getPlayer(account.getName()).getDisplayName()
                    + " §7(" + account.getFormatedBalance() + ")");
            rank++;
        }
        sender.sendMessage("");
    }
}
