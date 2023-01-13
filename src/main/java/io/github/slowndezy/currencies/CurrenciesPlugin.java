package io.github.slowndezy.currencies;

import io.github.slowndezy.currencies.command.MoneyCommand;
import io.github.slowndezy.currencies.command.subcommands.*;
import io.github.slowndezy.currencies.entities.Account;
import io.github.slowndezy.currencies.entities.services.AccountServices;
import io.github.slowndezy.currencies.integrations.VaultIntegration;
import io.github.slowndezy.currencies.listeners.PlayerListener;
import io.github.slowndezy.currencies.storarge.Database;
import io.github.slowndezy.currencies.storarge.dao.AccountDao;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageHolder;
import me.saiintbrisson.minecraft.command.message.MessageType;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CurrenciesPlugin extends JavaPlugin {

    private Database database;
    private AccountDao accountDao;
    private AccountServices accountServices;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupDatabase();

        accountServices = new AccountServices(this);

        setupEconomy();
        setupSyncTask();
        setupCommands();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        for (Account account : accountServices.getAccounts()) {
            if (account.isToSync()) accountDao.insert(account);
        }

        database.close();
    }

    public void setupEconomy() {
        getServer().getServicesManager().register(
                Economy.class,
                new VaultIntegration(this),
                this,
                ServicePriority.Highest);
    }

    public void setupDatabase() {
        ConfigurationSection section = getConfig().getConfigurationSection("mysql");
        database = new Database(
                section.getString("address"),
                section.getString("username"),
                section.getString("password"),
                section.getString("database"),
                section.getString("table"));
        database.initialize();

        accountDao = new AccountDao(this);
        accountDao.createTable();
    }

    public void setupCommands() {
        BukkitFrame frame = new BukkitFrame(this);
        frame.registerCommands(
                new MoneyCommand(this),
                new TopCommand(this),
                new SetCommand(this),
                new RemoveCommand(this),
                new PayCommand(this),
                new HelpCommand(),
                new AddCommand(this));

        MessageHolder holder = frame.getMessageHolder();
        holder.setMessage(MessageType.INCORRECT_TARGET, "§cNenhum jogador encontrado.");
        holder.setMessage(MessageType.NO_PERMISSION, "§cVocê não possui permissão para executar este comando.");
        holder.setMessage(MessageType.ERROR, "§cOcorreu um erro enquanto o comando estava sendo executado!");
    }

    public void setupSyncTask() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Account account : accountServices.getAccounts()) {
                if (!account.isToSync()) return;

                accountDao.update(account);
            }
        }, 0L, 300L);
    }
}
