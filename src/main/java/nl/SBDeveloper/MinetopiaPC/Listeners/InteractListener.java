package nl.SBDeveloper.MinetopiaPC.Listeners;

import nl.SBDeveloper.MinetopiaPC.API.Computer;
import nl.SBDeveloper.MinetopiaPC.API.MtPCAPI;
import nl.SBDeveloper.MinetopiaPC.API.User;
import nl.SBDeveloper.MinetopiaPC.Main;
import nl.SBDeveloper.MinetopiaPC.Utils.DataManager;
import nl.SBDeveloper.MinetopiaPC.Utils.MainUtil;
import nl.SBDeveloper.MinetopiaPC.Utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;
import java.util.Optional;

public class InteractListener implements Listener {
    @EventHandler
    public void onClickComputer(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Optional<XMaterial> pcmat = XMaterial.matchXMaterial(Objects.requireNonNull(Main.getSConfig().getFile().getString("Instellingen.Computer.Material")));
        if (!pcmat.isPresent()) return;

        Material pcMaterial = pcmat.get().parseMaterial();

        if (e.getClickedBlock().getType() == pcMaterial) {
            //It's a computer
            if (!MtPCAPI.containsUser(e.getPlayer().getUniqueId())) {
                MainUtil.sendMessage("Algemeen.WachtwoordNietIngesteld", e.getPlayer());
                return;
            }

            User user = MtPCAPI.getUser(e.getPlayer().getUniqueId());

            if (!MtPCAPI.isComputer(e.getClickedBlock().getLocation())) {
                Computer pc = MtPCAPI.createComputer(e.getClickedBlock().getLocation());
                pc.addUser(user);
            }

            Computer pc = MtPCAPI.getComputer(e.getClickedBlock().getLocation());

            if (!DataManager.isSecondClick(e.getPlayer())) {
                //FIRST CLICK!!!
                DataManager.addPlayerToPCMap(e.getPlayer(), pc);
                MainUtil.sendMessage("Login.Invoer", e.getPlayer());
                return;
            }

            if (!DataManager.checkPlayerPC(e.getPlayer(), pc)) {
                //Incorrect password
                MainUtil.sendMessage("Login.Fout", e.getPlayer());
                return;
            }

            MainUtil.sendMessage("Login.Goed", e.getPlayer());
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> MtPCAPI.openComputer(pc, e.getPlayer()), /* 2 sec */20 * 2);
        }
    }
}
