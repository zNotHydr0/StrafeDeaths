package strafeland.club.strafedeaths;

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
        saveDefaultConfig();
        createMessagesFile();

        getCommand("death").setExecutor(new DeathCommand(this));
        getCommand("death").setTabCompleter(new DeathCommand(this));
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
    }

    public FileConfiguration getMessagesConfig() {
        return this.messagesConfig;
    }

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

    public String getMessage(String path) {
        String prefix = messagesConfig.getString("chat-messages.prefix", "&8[&cStrafeDeaths&8] ");
        String message = messagesConfig.getString("chat-messages." + path);

        if (message == null) return ChatColor.RED + "Message not found: " + path;

        return ChatColor.translateAlternateColorCodes('&', prefix + message);
    }

    public String getRawMessage(String path) {
        String message = messagesConfig.getString("chat-messages." + path);
        if (message == null) return "Message not found: " + path;
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}