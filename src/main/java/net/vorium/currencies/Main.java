package net.vorium.currencies;

import com.henryfabio.sqlprovider.connector.SQLConnector;
import lombok.Getter;
import me.lucko.helper.Schedulers;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import net.vorium.currencies.command.MoneyCommand;
import net.vorium.currencies.command.subcommands.*;
import net.vorium.currencies.entities.Account;
import net.vorium.currencies.entities.services.AccountServices;
import net.vorium.currencies.integrations.VaultIntegration;
import net.vorium.currencies.listeners.PlayerListener;
import net.vorium.currencies.storarge.DatabaseFactory;
import net.vorium.currencies.storarge.dao.AccountRepo;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin {

    private static Main instance;
    private LuckPerms luckPerms;

    private SQLConnector connector;
    private AccountRepo repository;

    private AccountServices accountServices;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        setupDatabase();

        accountServices = new AccountServices(this);

        setupEconomy();

        setupSyncTask();
        setupLuckPerms();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        setupCommands();
    }

    @Override
    public void onDisable() {
        for (Account account : accountServices.getAccounts()) {
            if (account.isToSync()) repository.insertOne(account);
        }
    }

    public static Main getInstance() {
        return instance;
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

    public void setupLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        luckPerms = provider.getProvider();
    }

    public void setupCommands() {
        BukkitFrame frame = new BukkitFrame(this);
        frame.registerCommands(
                new MoneyCommand(this),
                new TopCommand(this),
                new SetCommand(this),
                new RemoveCommand(this),
                new PayCommand(this),
                new AddCommand(this));
    }

    public void setupSyncTask() {
        Schedulers.sync().runRepeating(() -> {
            for (Account account : accountServices.getAccounts()) {
                if (!account.isToSync()) return;

                repository.update(account);
            }
        }, 0L, 300L);
    }
}
