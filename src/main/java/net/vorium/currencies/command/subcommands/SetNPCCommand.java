package net.vorium.currencies.command.subcommands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import net.vorium.currencies.Main;
import net.vorium.currencies.command.MoneyCommand;
import org.bukkit.entity.Player;

public class SetNPCCommand extends MoneyCommand {

    public SetNPCCommand(Main plugin) {
        super(plugin);
    }

    @Command(name = "setnpc", aliases = { "settop" }, desc = "Coloque um NPC do ranking de coins.")
    public void setNPCCommand(@Sender Player player, int position) {

    }
}
