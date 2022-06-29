package net.vorium.currencies;

import com.henryfabio.sqlprovider.connector.SQLConnector;
import lombok.Getter;
import me.lucko.helper.Schedulers;
import net.milkbowl.vault.economy.Economy;
import net.vorium.currencies.commands.MoneyCommand;
import net.vorium.currencies.entities.Account;
import net.vorium.currencies.entities.services.AccountServices;
import net.vorium.currencies.integrations.VaultIntegration;
import net.vorium.currencies.listeners.PlayerListener;
import net.vorium.currencies.storarge.DatabaseFactory;
import net.vorium.currencies.storarge.dao.AccountRepo;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin {

    private static Main instance;
    private SQLConnector connector;
    private AccountRepo repository;
    private AccountServices services;

    @Override
    public void onEnable() {
        instance = this;

        setupEconomy();
        setupDatabase();
        setupSyncTask();

        saveDefaultConfig();
        services = new AccountServices(this);

        new MoneyCommand(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        for (Account account : services.getAccounts()) {
            if (account.isToSync()) repository.insertOne(account);
        }
    }

    public void setupEconomy() {
        getServer().getServicesManager().register(
                Economy.class,
                new VaultIntegration(this),
                this,
                ServicePriority.Highest);
    }

    public void setupDatabase() {
        connector = DatabaseFactory.createConnection(getConfig().getConfigurationSection("mysql"));
        repository = new AccountRepo(this);
        repository.createTable();
    }

    public void setupSyncTask() {
        Schedulers.sync().runRepeating(() -> {
            for (Account account : services.getAccounts()) {
                if (account.isToSync()) {
                    repository.insertOne(account);
                }
            }
        }, 0L, 80L);
    }

}
