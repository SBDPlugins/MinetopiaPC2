package nl.SBDeveloper.MinetopiaPC.Listeners;

import nl.SBDeveloper.MinetopiaPC.API.MtPCAPI;
import nl.SBDeveloper.MinetopiaPC.API.User;
import nl.SBDeveloper.MinetopiaPC.Utils.DataManager;
import nl.SBDeveloper.MinetopiaPC.Utils.MainUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e) {
        if (DataManager.isFirstClick(e.getPlayer())) {
            e.setCancelled(true);

            String password = e.getMessage();

            if (password.equalsIgnoreCase("STOP")) DataManager.stopLogin(e.getPlayer());

            User user = MtPCAPI.getUser(e.getPlayer().getUniqueId());

            DataManager.addPlayerToPlayerMap(e.getPlayer(), password.equals(user.getPassword()));

            MainUtil.sendMessage("Login.Invoer2", e.getPlayer());
        }
    }

}
