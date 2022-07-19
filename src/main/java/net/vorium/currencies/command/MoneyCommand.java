package net.vorium.currencies.command;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import net.vorium.currencies.Main;
import net.vorium.currencies.entities.Account;
import net.vorium.currencies.entities.services.AccountServices;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class MoneyCommand {

    public final AccountServices services;

    public final DecimalFormat format = new DecimalFormat("#,###.#");

    public MoneyCommand(Main plugin) {
        this.services = plugin.getAccountServices();
    }

    @Command(name = "money", aliases = { "coins", "moedas"})
    public void moneyCommand(Player sender, @Optional String target) {
        Account account = services.findByName(target);

        if (account == null) {
            account = services.findByName(sender.getName());
            sender.sendMessage("§eVocê possui: §a" + account.getFormatedBalance() + " coins");
        } else {
            sender.sendMessage(account.getPrefix() + account.getName() + " §epossui: §a" + account.getFormatedBalance() + " coins");
        }
    }
}
