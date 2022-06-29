package net.vorium.currencies.commands;

import me.lucko.helper.Commands;
import net.vorium.currencies.Main;
import net.vorium.currencies.entities.Account;
import net.vorium.currencies.entities.services.AccountServices;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class MoneyCommand {

    private final AccountServices services;

    public MoneyCommand(Main plugin) {
        this.services = plugin.getServices();

        handleMainCommand();
        handeleSubCommands();
    }

    public void handleMainCommand() {
        Commands.create()
                .assertPlayer()
                .assertUsage("<player>")
                .handler(command -> {
                    Player sender = command.sender();
                    Optional<Player> other = command.arg(0).parse(Player.class);
                    Account account = services.findByName(sender.getName());

                    if (other.isPresent()) {
                        account = services.findByName(other.get().getName());
                    }

                    sender.sendMessage(other.map(player -> player.getName() + " possui: §").orElse("§eVocê possui: §a") + account.getBalance() + " coins");

                }).register("money", "coins", "moedas");
    }

    public void handeleSubCommands() {
        Commands.create()
                .handler(command -> {
                    CommandSender sender = command.sender();
                    sender.sendMessage( new String[] { "", "§2 Top 10 jogadores mais ricos", ""});

                    int rank = 0;
                    for (Account account : services.getRanking(10)) {
                        sender.sendMessage("§f  " + rank + ". "
                                + account.getName()
                                + " §7(" + account.getBalance() + ")");
                        rank++;
                    }
                    sender.sendMessage("");

                })
                .register("money top", "coins top", "moedas top");
        // money top
        // money pay
        // money add
        // money set
        // money remove
        // money helper
    }
}
