package nl.SBDeveloper.MinetopiaPC.API;

import nl.SBDeveloper.MinetopiaPC.Main;
import nl.SBDeveloper.MinetopiaPC.Utils.ItemBuilder;
import nl.SBDeveloper.MinetopiaPC.Utils.MainUtil;
import nl.SBDeveloper.MinetopiaPC.Utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

public class MtPCAPI {
    private static LinkedHashMap<Location, Computer> computerMap = new LinkedHashMap<>();

    public static void createComputer(Location loc) {
        Computer comp = new Computer(loc);
        computerMap.put(loc, comp);
    }

    public static void removeComputer(Location loc) {
        computerMap.remove(loc);
    }

    @Nullable
    public static Computer getComputer(Location loc) {
        if (computerMap.containsKey(loc)) {
            return computerMap.get(loc);
        }
        return null;
    }

    public static void openComputer(Computer computer, Player p) {
        Inventory SCGUI = Bukkit.createInventory(null, 27, ChatColor.DARK_BLUE + "Computer");

        for (String soort : Main.getSConfig().getFile().getConfigurationSection("Instellingen.GUIItems").getKeys(false)) {
            String mat = Main.getSConfig().getFile().getString("Instellingen.GUIItems." + soort + ".Material");

            if (mat == null) return;

            Optional<XMaterial> xmat = XMaterial.matchXMaterial(mat);

            if (!xmat.isPresent()) return;

            ItemBuilder item = new ItemBuilder(xmat.get().parseItem());

            item.setName(MainUtil.formatColors(Main.getSConfig().getFile().getString("Instellingen.GUIItems." + soort + ".Naam")));

            ArrayList<String> lore;
            if (soort.equalsIgnoreCase("Afsluiten")) {
                lore = MainUtil.getCloseLores(p);
            } else {
                lore = new ArrayList<>();
                for(String str : Main.getSConfig().getFile().getStringList("Instellingen.GUIItems." + soort + ".Lores")) {
                    lore.add(MainUtil.formatColors(str));
                }
            }
            item.setLore(lore);

            SCGUI.setItem(Main.getSConfig().getFile().getInt("Instellingen.GUIItems." + soort + ".Slot"), item.toItemStack());
        }

        p.openInventory(SCGUI);
    }
}
