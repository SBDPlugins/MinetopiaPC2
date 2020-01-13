package nl.SBDeveloper.MinetopiaPC.Listeners;

import nl.SBDeveloper.MinetopiaPC.API.MtPCAPI;
import nl.SBDeveloper.MinetopiaPC.Main;
import nl.SBDeveloper.MinetopiaPC.Utils.MainUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(MainUtil.formatColors(Main.getSConfig().getFile().getString("Instellingen.GUI.Naam")))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null
                    || e.getCurrentItem().getType() == Material.AIR || !(e.getWhoClicked() instanceof Player)) return;

            ItemStack clicked = e.getCurrentItem();
            Player p = (Player) e.getWhoClicked();

            if (clicked.getType() == MtPCAPI.getGUIItem("Weerbericht")) {
                if (!p.hasPermission(MtPCAPI.getGUIPermission("Weerbericht"))) {
                    p.sendMessage(MainUtil.formatColors(Main.getMessages().getFile().getString("Berichten.Algemeen.GeenGebruikPermissie")));
                    return;
                }
                Bukkit.dispatchCommand(p, "weer");
            } else if (clicked.getType() == MtPCAPI.getGUIItem("HoogsteFitheid")) {
                if (!p.hasPermission(MtPCAPI.getGUIPermission("HoogsteFitheid"))) {
                    p.sendMessage(MainUtil.formatColors(Main.getMessages().getFile().getString("Berichten.Algemeen.GeenGebruikPermissie")));
                    return;
                }
                Bukkit.dispatchCommand(p, "stattop fitheid");
            } else if (clicked.getType() == MtPCAPI.getGUIItem("MeesteGeld")) {
                if (!p.hasPermission(MtPCAPI.getGUIPermission("MeesteGeld"))) {
                    p.sendMessage(MainUtil.formatColors(Main.getMessages().getFile().getString("Berichten.Algemeen.GeenGebruikPermissie")));
                    return;
                }
                Bukkit.dispatchCommand(p, "stattop money");
            } else if (clicked.getType() == MtPCAPI.getGUIItem("HoogsteLevel")) {
                if (!p.hasPermission(MtPCAPI.getGUIPermission("HoogsteLevel"))) {
                    p.sendMessage(MainUtil.formatColors(Main.getMessages().getFile().getString("Berichten.Algemeen.GeenGebruikPermissie")));
                    return;
                }
                Bukkit.dispatchCommand(p, "stattop level");
            } else if (clicked.getType() == MtPCAPI.getGUIItem("Bankieren")) {
                if (!p.hasPermission(MtPCAPI.getGUIPermission("Bankieren"))) {
                    p.sendMessage(MainUtil.formatColors(Main.getMessages().getFile().getString("Berichten.Algemeen.GeenGebruikPermissie")));
                    return;
                }
                Bukkit.dispatchCommand(p, "openbank");
            } else if (clicked.getType() == MtPCAPI.getGUIItem("Prullenbak")) {
                if (!p.hasPermission(MtPCAPI.getGUIPermission("Prullenbak"))) {
                    p.sendMessage(MainUtil.formatColors(Main.getMessages().getFile().getString("Berichten.Algemeen.GeenGebruikPermissie")));
                    return;
                }
                Bukkit.dispatchCommand(p, "prullenbak");
            } else if (clicked.getType() == MtPCAPI.getGUIItem("Afsluiten")) {
                p.sendMessage(MainUtil.formatColors(Main.getMessages().getFile().getString("Berichten.Algemeen.ComputerAfgesloten")));
                p.closeInventory();
            }
        }
    }
}
