package com.me.tft_02.mcmmoredeem.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import com.me.tft_02.mcmmoredeem.config.Config;
import com.me.tft_02.mcmmoredeem.datatypes.PlayerData;
import com.me.tft_02.mcmmoredeem.util.CreditsManager;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLogin(PlayerLoginEvent event) {
        int startupAmount = Config.getInstance().getStartupAmount();

        if (startupAmount <= 0) {
            return;
        }

        PlayerData playerData = CreditsManager.addPlayerData(event.getPlayer());
        playerData.setCredits(startupAmount);
    }

    /**
     * Monitor PlayerJoinEvents.
     * <p>
     * These events are monitored for the purpose of initializing player
     * variables, as well as handling the MOTD display and other important
     * join messages.
     *
     * @param event The event to monitor
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!Config.getInstance().getJoinMessageEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        int credits  = CreditsManager.getCredits(player.getUniqueId());

        if (credits > 0) {
            player.sendMessage(ChatColor.GREEN + "You have " + ChatColor.GOLD + credits + ChatColor.GREEN + " MCMMO credits.");
            player.sendMessage(ChatColor.GREEN + "Use " + ChatColor.GOLD + "/redeem <skill> <amount>" + ChatColor.GREEN + " to redeem them.");
        }
    }
}
