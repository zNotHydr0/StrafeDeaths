package strafeland.club.strafedeaths.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import strafeland.club.strafedeaths.Main;
import java.util.List;

public class DeathListener implements Listener {

    private final Main plugin;

    public DeathListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        String worldName = victim.getWorld().getName();
        EntityDamageEvent lastDamage = victim.getLastDamageCause();

        if (plugin.getConfig().getBoolean("settings.thunder-enabled")) {
            victim.getWorld().strikeLightningEffect(victim.getLocation());
        }

        List<String> disabledWorlds = plugin.getConfig().getStringList("settings.disabled-worlds");
        if (disabledWorlds.contains(worldName)) {
            event.setDeathMessage(null);
            return;
        }

        String newMessage;
        String victimName = victim.getName();

        if (victim.getKiller() != null) {
            newMessage = plugin.getRawMessage("death-player")
                    .replace("%victim%", victimName)
                    .replace("%killer%", victim.getKiller().getName());
        } else if (lastDamage != null && (lastDamage.getCause() == EntityDamageEvent.DamageCause.FALL || lastDamage.getCause() == EntityDamageEvent.DamageCause.VOID)) {
            newMessage = plugin.getRawMessage("death-void-fall")
                    .replace("%victim%", victimName);
        } else if (lastDamage instanceof EntityDamageByEntityEvent && isMob(((EntityDamageByEntityEvent) lastDamage).getDamager())) {
            Entity damager = ((EntityDamageByEntityEvent) lastDamage).getDamager();
            newMessage = plugin.getRawMessage("death-mob")
                    .replace("%victim%", victimName)
                    .replace("%mob%", damager.getName());
        } else {
            newMessage = plugin.getRawMessage("death-general")
                    .replace("%victim%", victimName);
        }

        if (newMessage != null) {
            event.setDeathMessage(newMessage);
        }
    }

    private boolean isMob(Entity entity) {
        return entity instanceof LivingEntity && !(entity instanceof Player);
    }
}