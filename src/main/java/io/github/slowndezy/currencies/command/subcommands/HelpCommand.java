package io.github.slowndezy.currencies.command.subcommands;

import io.github.slowndezy.currencies.config.MessagesConfig;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.entity.Player;

public class HelpCommand {

    @Command(name = "money.help", aliases = "ajuda")
    public void helpCommand(Context<Player> sender) {
        if (!sender.testPermission("currencies.admin", true)) {
            MessagesConfig.get(MessagesConfig::helpCommand).forEach(sender::sendMessage);
        } else {
            MessagesConfig.get(MessagesConfig::helpAdminCommand).forEach(sender::sendMessage);
        }
    }
}
