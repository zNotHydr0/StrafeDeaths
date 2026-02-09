package strafeland.club.strafedeaths.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import strafeland.club.strafedeaths.Main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeathCommand implements CommandExecutor, TabCompleter {

    private final Main plugin;

    public DeathCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("strafedeaths.admin")) {
            sender.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "help":
                sendHelp(sender);
                break;

            case "reload":
                plugin.reloadConfig();
                plugin.reloadMessages();
                sender.sendMessage(plugin.getMessage("reload"));
                break;

            case "thunder":
            case "thunders":
                boolean current = plugin.getConfig().getBoolean("settings.thunder-enabled");
                plugin.getConfig().set("settings.thunder-enabled", !current);
                plugin.saveConfig();
                sender.sendMessage(plugin.getMessage(!current ? "thunder-enabled" : "thunder-disabled"));
                break;

            case "notification":
            case "notifications":
                handleNotifications(sender, args);
                break;

            default:
                sendHelp(sender);
                break;
        }

        return true;
    }

    private void handleNotifications(CommandSender sender, String[] args) {
        World world;
        if (args.length > 1) {
            world = Bukkit.getWorld(args[1]);
        } else {
            if (sender instanceof Player) {
                world = ((Player) sender).getWorld();
            } else {
                sender.sendMessage(plugin.getMessage("world-not-found").replace("%world%", "Console"));
                return;
            }
        }

        if (world == null) {
            sender.sendMessage(plugin.getMessage("world-not-found").replace("%world%", args.length > 1 ? args[1] : "null"));
            return;
        }

        List<String> disabledWorlds = plugin.getConfig().getStringList("settings.disabled-worlds");
        String worldName = world.getName();

        if (disabledWorlds.contains(worldName)) {
            disabledWorlds.remove(worldName);
            sender.sendMessage(plugin.getMessage("notifications-enabled-world").replace("%world%", worldName));
        } else {
            disabledWorlds.add(worldName);
            sender.sendMessage(plugin.getMessage("notifications-disabled-world").replace("%world%", worldName));
        }

        plugin.getConfig().set("settings.disabled-worlds", disabledWorlds);
        plugin.saveConfig();
    }

    private void sendHelp(CommandSender sender) {
        List<String> helpList = plugin.getMessagesConfig().getStringList("chat-messages.help");
        for (String line : helpList) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("strafedeaths.admin")) return Collections.emptyList();

        if (args.length == 1) {
            List<String> subcommands = Arrays.asList("help", "reload", "thunder", "thunders", "notification", "notifications");
            return filter(subcommands, args[0]);
        }

        if (args.length == 2 && (args[0].equalsIgnoreCase("notification") || args[0].equalsIgnoreCase("notifications"))) {
            List<String> worlds = new ArrayList<>();
            for (World world : Bukkit.getWorlds()) {
                worlds.add(world.getName());
            }
            return filter(worlds, args[1]);
        }

        return Collections.emptyList();
    }

    private List<String> filter(List<String> list, String query) {
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (s.toLowerCase().startsWith(query.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }
}