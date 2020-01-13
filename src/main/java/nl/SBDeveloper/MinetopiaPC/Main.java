package nl.SBDeveloper.MinetopiaPC;

import nl.SBDeveloper.MinetopiaPC.Commands.ComputerCommand;
import nl.SBDeveloper.MinetopiaPC.Listeners.GUIClickListener;
import nl.SBDeveloper.MinetopiaPC.Listeners.InteractListener;
import nl.SBDeveloper.MinetopiaPC.Listeners.JoinQuitListeners;
import nl.SBDeveloper.MinetopiaPC.Utils.SBYamlFile;
import nl.SBDeveloper.MinetopiaPC.Utils.UpdateManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private static SBYamlFile config;
    private static SBYamlFile data;
    private static SBYamlFile messages;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("[MinetopiaPC] Bezig met het laden van v" + getDescription().getVersion() + " van MinetopiaPC!");
        instance = this;

        if (Bukkit.getPluginManager().getPlugin("MinetopiaSDB") == null) {
            Bukkit.getConsoleSender().sendMessage("[MinetopiaPC] " + ChatColor.RED + "Oeps! Voor MinetopiaPC is MinetopiaSDB benodigd! Plugin wordt uigeschakeld...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        config = new SBYamlFile(this, "config");
        data = new SBYamlFile(this, "data");
        messages = new SBYamlFile(this, "messages");

        config.loadDefaults();
        messages.loadDefaults();

        loadCommands();
        loadListeners();

        new UpdateManager(this, 55092, UpdateManager.CheckType.SPIGOT).handleResponse((versionResponse, version) -> {
            if (versionResponse == UpdateManager.VersionResponse.FOUND_NEW) {
                Bukkit.getLogger().warning("[MinetopiaPC] Er is een update beschikbaar! Huidig: " + this.getDescription().getVersion() + " Nieuw: " + version);
            } else if (versionResponse == UpdateManager.VersionResponse.LATEST) {
                Bukkit.getLogger().info("[MinetopiaPC] Je hebt de laatste versie [" + this.getDescription().getVersion() + "]!");
            } else if (versionResponse == UpdateManager.VersionResponse.UNAVAILABLE) {
                Bukkit.getLogger().severe("[MinetopiaPC] Kon niet checken op een update.");
            }
        }).check();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void loadCommands() {
        getCommand("computer").setExecutor(new ComputerCommand());
    }

    private void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new JoinQuitListeners(), this);
        Bukkit.getPluginManager().registerEvents(new GUIClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public static SBYamlFile getSConfig() {
        return config;
    }

    public static SBYamlFile getData() {
        return data;
    }

    public static SBYamlFile getMessages() {
        return messages;
    }
}
