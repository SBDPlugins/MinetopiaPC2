package nl.SBDeveloper.MinetopiaPC.API;

import nl.SBDeveloper.MinetopiaPC.Main;
import nl.SBDeveloper.MinetopiaPC.Utils.ItemBuilder;
import nl.SBDeveloper.MinetopiaPC.Utils.MainUtil;
import nl.SBDeveloper.MinetopiaPC.Utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.UUID;

public class MtPCAPI {
    private static LinkedHashMap<Location, Computer> computerMap = new LinkedHashMap<>();
    private static LinkedHashMap<UUID, User> userMap = new LinkedHashMap<>();

    @Nonnull
    public static Computer createComputer(Location loc) {
        Computer comp = new Computer(loc);
        computerMap.put(loc, comp);
        return comp;
    }

    public static void removeComputer(Location loc) {
        computerMap.remove(loc);
    }

    public static boolean isComputer(Location loc) {
        return computerMap.containsKey(loc);
    }

    @Nullable
    public static Computer getComputer(Location loc) {
        if (computerMap.containsKey(loc)) {
            return computerMap.get(loc);
        }
        return null;
    }

    @Nullable
    public static Material getGUIItem(String soort) {
        if(!Main.getSConfig().getFile().contains("Instellingen.GUI.Items." + soort + ".Material")) return null;
        String mat = Main.getSConfig().getFile().getString("Instellingen.GUI.Items." + soort + ".Material");
        if (mat == null) return null;
        Optional<XMaterial> xmat = XMaterial.matchXMaterial(mat);
        return xmat.map(XMaterial::parseMaterial).orElse(null);
    }

    public static String getGUIPermission(String soort) {
        return Main.getSConfig().getFile().getString("Instellingen.GUI.Items." + soort + ".Permissie");
    }

    public static void openComputer(Computer computer, Player p) {
        Inventory SCGUI = Bukkit.createInventory(null, 27, MainUtil.formatColors(Main.getSConfig().getFile().getString("Instellingen.GUI.Naam")));

        for (String soort : Main.getSConfig().getFile().getConfigurationSection("Instellingen.GUI.Items").getKeys(false)) {
            String mat = Main.getSConfig().getFile().getString("Instellingen.GUI.Items." + soort + ".Material");

            if (mat == null) return;

            Optional<XMaterial> xmat = XMaterial.matchXMaterial(mat);

            if (!xmat.isPresent()) return;

            ItemBuilder item = new ItemBuilder(xmat.get().parseItem());

            item.setName(MainUtil.formatColors(Main.getSConfig().getFile().getString("Instellingen.GUI.Items." + soort + ".Naam")));

            ArrayList<String> lore;
            if (soort.equalsIgnoreCase("Afsluiten")) {
                lore = MainUtil.getCloseLores(p);
            } else {
                lore = new ArrayList<>();
                for(String str : Main.getSConfig().getFile().getStringList("Instellingen.GUI.Items." + soort + ".Lores")) {
                    lore.add(MainUtil.formatColors(str));
                }
            }
            item.setLore(lore);

            SCGUI.setItem(Main.getSConfig().getFile().getInt("Instellingen.GUI.Items." + soort + ".Slot"), item.toItemStack());
        }

        p.openInventory(SCGUI);
    }

    public static void addUser(User user) {
        userMap.put(user.getPlayerUUID(), user);
    }

    public static void removeUser(UUID user) {
        userMap.remove(user);
    }

    public static void setPassword(UUID user, String password) {
        userMap.get(user).setPassword(password);
    }

    public static boolean containsUser(UUID user) {
        return userMap.containsKey(user);
    }

    public static User getUser(UUID user) { return userMap.get(user); }
}
