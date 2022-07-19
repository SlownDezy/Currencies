package net.vorium.currencies.command.subcommands;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import net.vorium.currencies.Main;
import net.vorium.currencies.command.MoneyCommand;
import org.bukkit.entity.Player;

public class SetNPCCommand extends MoneyCommand {

    private Main plugin;

    public SetNPCCommand(Main plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Command(name = "money.setnpc", aliases = { "settop" })
    public void setNPCCommand(Context<Player> player, int position) {
        //plugin.getRankingServices().create(position, player.getLocation());
        player.sendMessage("§aNPC criado com sucesso!");
    }
}
