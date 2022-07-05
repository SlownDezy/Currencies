package net.vorium.currencies.command.subcommands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import net.vorium.currencies.Main;
import net.vorium.currencies.command.MoneyCommand;
import net.vorium.currencies.entities.Account;
import org.bukkit.command.CommandSender;

public class TopCommand extends MoneyCommand {

    public TopCommand(Main plugin) {
        super(plugin);
    }

    @Command(name = "top", aliases = "ranking", desc = "Veja os 10 jogadores com mais moedas do servidor.")
    public void topCommand(@Sender CommandSender sender) {
        sender.sendMessage( new String[] { "", "§2 Top 10 jogadores mais ricos", ""});

        int rank = 1;
        for (Account account : services.getRanking(10)) {
            sender.sendMessage("§f  " + rank + ". "
                    + account.getPrefix()
                    + account.getName()
                    + " §7(" + account.getFormatedBalance() + ")");
            rank++;
        }
        sender.sendMessage("");
    }
}
