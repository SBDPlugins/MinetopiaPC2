package nl.SBDeveloper.MinetopiaPC.Commands;

import nl.SBDeveloper.MinetopiaPC.API.User;
import nl.SBDeveloper.MinetopiaPC.Main;
import nl.SBDeveloper.MinetopiaPC.Managers.DataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComputerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!label.equalsIgnoreCase("computer")) return true;

        if (args.length == 0) {
            return helpCommand(sender, label, args);
        } else if (args[0].equalsIgnoreCase("info") && args.length == 1) {
            return infoCommand(sender, label, args);
        } else if (args[0].equalsIgnoreCase("setpassword") && args.length == 2) {
            if (sender instanceof Player) {
                return setCodeCommand(sender, label, args);
            }
        }

        return helpCommand(sender, label, args);
    }


    private boolean setCodeCommand(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;
        if (DataManager.containsUser(p.getUniqueId())) {
            DataManager.setPassword(p.getUniqueId(), args[1]);
        } else {
            User user = new User(p.getUniqueId(), args[1]);
            DataManager.addUser(user);
        }
        return true;
    }

    private boolean infoCommand(CommandSender sender, String label, String[] args) {
        sender.sendMessage("§1==================================");
        sender.sendMessage("§6MinetopiaPC plugin gemaakt door §aSBDeveloper");
        sender.sendMessage("§6Versie: " + Main.getInstance().getDescription().getVersion());
        sender.sendMessage("§6Type /computer help voor meer informatie!");
        sender.sendMessage("§1==================================");
        return true;
    }

    private boolean helpCommand(CommandSender sender, String label, String[] args) {
        sender.sendMessage("§9MinetopiaPC commands:");
        sender.sendMessage("§6/computer info§f: Geeft informatie over de plugin.");
        sender.sendMessage("§6/computer help§f: Geeft alle commando's.");

        sender.sendMessage("§6/computer setpassword <Wachtwoord>§f: Verander jouw Computer wachtwoord.");
        return true;
    }
}
