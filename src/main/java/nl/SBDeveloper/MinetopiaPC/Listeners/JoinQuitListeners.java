package nl.SBDeveloper.MinetopiaPC.Listeners;

import nl.SBDeveloper.MinetopiaPC.API.MtPCAPI;
import nl.SBDeveloper.MinetopiaPC.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!MtPCAPI.containsUser(p.getUniqueId())) {
            //New user! Ask user for his password.
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> p.sendMessage(ChatColor.BLUE + "Je hebt nog geen computerwachtwoord ingesteld! Gebruik /computer setpassword <Wachtwoord> om computers te gebruiken."), 20);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

    }

}
