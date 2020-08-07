package xyz.rusin.chunkyarmorstands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class Commands implements CommandExecutor, TabCompleter {
    public static final String COMMAND = "chunkyarmorstands";
    private static final String RELOAD_CONFIG = "reloadconfig";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1 || !args[0].equalsIgnoreCase(RELOAD_CONFIG)) {
            System.out.println("Unknown command!");
            return false;
        }
        System.out.println("Config is being reloaded!");
        StandChecker.SINGLETON.loadConfig();
        System.out.println("Done");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of(RELOAD_CONFIG);
    }
    
}