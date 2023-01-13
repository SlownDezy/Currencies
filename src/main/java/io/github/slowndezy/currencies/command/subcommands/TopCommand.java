package io.github.slowndezy.currencies.command.subcommands;

import io.github.slowndezy.currencies.CurrenciesPlugin;
import io.github.slowndezy.currencies.command.MoneyCommand;
import io.github.slowndezy.currencies.config.MessagesConfig;
import io.github.slowndezy.currencies.entities.Account;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.command.CommandSender;

public class TopCommand extends MoneyCommand {

    public TopCommand(CurrenciesPlugin plugin) {
        super(plugin);
    }

    @Command(name = "money.top", aliases = "ranking")
    public void topCommand(Context<CommandSender> sender) {
        MessagesConfig.get(MessagesConfig::header).forEach(sender::sendMessage);
        int rank = 1;
        for (Account account : services.getRanking(MessagesConfig.get(MessagesConfig::maxInTop))) {
            sender.sendMessage(MessagesConfig.get(MessagesConfig::rankingFormat)
                    .replace("{position}", String.valueOf(rank))
                    .replace("{player}", account.getName())
                    .replace("{coins}", account.getFormatedBalance()));
            rank++;
        }
        MessagesConfig.get(MessagesConfig::footer).forEach(sender::sendMessage);
    }
}
