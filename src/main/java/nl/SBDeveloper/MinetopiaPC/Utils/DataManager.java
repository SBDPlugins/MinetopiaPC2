package nl.SBDeveloper.MinetopiaPC.Utils;

import nl.SBDeveloper.MinetopiaPC.API.Computer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.WeakHashMap;

public class DataManager {
    //Speler klikt 1e keer op PC -> Voeg toe aan Map met Speler & PC
    //Speler typt wachtwoord in -> Voeg toe aan Map met Speler & boolean
    //Speler klikt 2e keer op PC -> Check of in map 1 en 2, check of map 1 PC hetzelfde is, en check op boolean

    private static WeakHashMap<UUID, Computer> computerPasswordMap = new WeakHashMap<>();
    private static WeakHashMap<UUID, Boolean> playerPasswordMap = new WeakHashMap<>();

    public static void addPlayerToPCMap(@Nonnull Player p, Computer pc) {
        computerPasswordMap.put(p.getUniqueId(), pc);
    }

    public static void addPlayerToPlayerMap(@Nonnull Player p, boolean password) {
        playerPasswordMap.put(p.getUniqueId(), password);
    }

    public static boolean isFirstClick(@Nonnull Player p) {
        return computerPasswordMap.containsKey(p.getUniqueId()) && !playerPasswordMap.containsKey(p.getUniqueId());
    }

    public static boolean isSecondClick(@Nonnull Player p) {
        return computerPasswordMap.containsKey(p.getUniqueId()) && playerPasswordMap.containsKey(p.getUniqueId());
    }

    public static void stopLogin(@Nonnull Player p) {
        computerPasswordMap.remove(p.getUniqueId());
    }

    public static boolean checkPlayerPC(@Nonnull Player p, Computer pc) {
        if (!computerPasswordMap.containsKey(p.getUniqueId()) || !playerPasswordMap.containsKey(p.getUniqueId())) return false;

        Computer mapPC = computerPasswordMap.get(p.getUniqueId());

        if (!pc.getLocation().equals(mapPC.getLocation())) return false;

        boolean password = playerPasswordMap.get(p.getUniqueId());

        computerPasswordMap.remove(p.getUniqueId());
        playerPasswordMap.remove(p.getUniqueId());

        return password;
    }
}
