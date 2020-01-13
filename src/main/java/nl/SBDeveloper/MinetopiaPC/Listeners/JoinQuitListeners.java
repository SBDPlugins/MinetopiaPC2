package nl.SBDeveloper.MinetopiaPC.Listeners;

import nl.SBDeveloper.MinetopiaPC.API.MtPCAPI;
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
            p.sendMessage("Je hebt nog geen computerwachtwoord ingesteld! Gebruik /computer setpassword <Wachtwoord> om computers te gebruiken.");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

    }

}
