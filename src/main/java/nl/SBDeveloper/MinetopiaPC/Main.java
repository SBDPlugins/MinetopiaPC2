package nl.SBDeveloper.MinetopiaPC;

import nl.SBDeveloper.MinetopiaPC.Commands.ComputerCommand;
import nl.SBDeveloper.MinetopiaPC.Utils.SBYamlFile;
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
        instance = this;

        if (Bukkit.getPluginManager().getPlugin("MinetopiaSDB") == null) {
            Bukkit.getConsoleSender().sendMessage("[MinetopiaPC] " + ChatColor.RED + "Oeps! Voor MinetopiaPC is MinetopiaSDB benodigd! Plugin wordt uigeschakeld...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        config = new SBYamlFile(this, "config");
        data = new SBYamlFile(this, "data");
        messages = new SBYamlFile(this, "messages");

        loadCommands();
        loadListeners();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void loadCommands() {
        getCommand("computer").setExecutor(new ComputerCommand());
    }

    private void loadListeners() {

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
