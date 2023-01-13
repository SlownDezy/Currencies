package io.github.slowndezy.currencies.listeners;

import io.github.slowndezy.currencies.CurrenciesPlugin;
import io.github.slowndezy.currencies.entities.Account;
import io.github.slowndezy.currencies.storarge.dao.AccountDao;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final CurrenciesPlugin plugin;

    public PlayerListener(CurrenciesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AccountDao accountDao = plugin.getAccountDao();

        accountDao.findByName(player.getName()).thenAccept(account -> {
            if (account == null) {
                account = new Account(player.getName());
                accountDao.insert(account);
                plugin.getAccountServices().registerAccount(account);
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        AccountDao accountDao = plugin.getAccountDao();

        accountDao.findByName(player.getName())
                .thenAccept(account -> {
                    if (account == null || !account.isToSync()) return;

                    accountDao.update(account);
                    account.setToSync(false);
                });
    }
}
