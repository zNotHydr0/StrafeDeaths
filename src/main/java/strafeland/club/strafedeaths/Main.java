package strafeland.club.strafedeaths;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import strafeland.club.strafedeaths.commands.DeathCommand;
import strafeland.club.strafedeaths.listeners.DeathListener;
import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    private File messagesFile;
    private FileConfiguration messagesConfig;

    @Override
    public void onEnable() {
        // Load Config and Messages
        saveDefaultConfig();
        createMessagesFile();

        // Register Commands and Events
        getCommand("death").setExecutor(new DeathCommand(this));
        getCommand("death").setTabCompleter(new DeathCommand(this));
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);

        // Notify Console
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "StrafeDeaths" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " Plugin has been enabled successfully. Created by zNotHydr0 :)");
    }

    public FileConfiguration getMessagesConfig() {
        return this.messagesConfig;
    }

    //Reloads the custom messages.yml file from the disk
    public void reloadMessages() {
        messagesFile = new File(getDataFolder(), "messages.yml");
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void saveMessages() {
        try {
            messagesConfig.save(messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMessagesFile() {
        messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    // Retrieves a message from messages.yml, adds the global prefix, and colors it
    public String getMessage(String path) {
        String prefix = messagesConfig.getString("chat-messages.prefix", "&8[&cStrafeDeaths&8] ");
        String message = messagesConfig.getString("chat-messages." + path);

        if (message == null) return ChatColor.RED + "Message not found: " + path;

        return ChatColor.translateAlternateColorCodes('&', prefix + message);
    }

    // Retrieves a raw message without the prefix
    public String getRawMessage(String path) {
        String message = messagesConfig.getString("chat-messages." + path);
        if (message == null) return "Message not found: " + path;
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}