package net.vorium.currencies.listeners;

import me.lucko.helper.Schedulers;
import net.vorium.currencies.Main;
import net.vorium.currencies.entities.Account;
import net.vorium.currencies.entities.services.AccountServices;
import net.vorium.currencies.storarge.dao.AccountRepo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final AccountServices services;
    private final AccountRepo repository;

    public PlayerListener(Main plugin) {
        services = plugin.getAccountServices();
        repository = plugin.getRepository();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Schedulers.async().execute(() -> {
            Account account = repository.findByName(player.getName());

            if (account == null) {
                account = new Account(player.getName());
                repository.insertOne(account);
                services.registerAccount(account);
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Schedulers.async().execute(() -> {
            Account account = repository.findByName(player.getName());
            if (account == null || !account.isToSync()) return;

            repository.update(account);
            account.setToSync(false);
        });
    }
}
