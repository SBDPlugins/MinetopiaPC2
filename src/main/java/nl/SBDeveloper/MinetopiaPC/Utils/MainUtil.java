package nl.SBDeveloper.MinetopiaPC.Utils;

import nl.SBDeveloper.MinetopiaPC.Main;
import nl.minetopiasdb.api.SDBPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainUtil {
    @Nonnull
    public static void sendMessage(String path, @Nonnull Player p) {
        p.sendMessage(formatColors(Main.getMessages().getFile().getString("Berichten." + path)));
    }

    @Nonnull
    public static String formatColors(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    @Nonnull
    public static ArrayList<String> getCloseLores(@Nonnull Player p) {
        SDBPlayer sdb = SDBPlayer.createSDBPlayer(p.getUniqueId());

        ArrayList<String> lore = new ArrayList<>();
        for (String str : Main.getSConfig().getFile().getStringList("Instellingen.GUI.Items.Afsluiten.Lores")) {

            StringBuffer buf = new StringBuffer();
            Matcher m = Pattern.compile("%(.*?)%").matcher(str);
            while (m.find()) {
                switch (m.group()) {
                    case "SpelerNaam":
                        m.appendReplacement(buf, p.getName());
                        break;
                    case "Rang":
                        m.appendReplacement(buf, sdb.getRank());
                        break;
                    case "Geld":
                        m.appendReplacement(buf, String.format("%.2f", sdb.getBalance()));
                        break;
                    case "Level":
                        m.appendReplacement(buf, String.valueOf(sdb.getLevel()));
                        break;
                    default:
                        break;
                }
            }
            String replaced = m.appendTail(buf).toString();

            lore.add(formatColors(replaced));
        }

        return lore;
    }
}
