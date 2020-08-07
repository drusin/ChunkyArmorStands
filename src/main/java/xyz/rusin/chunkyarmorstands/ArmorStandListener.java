package xyz.rusin.chunkyarmorstands;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ArmorStandListener implements Listener {

    @EventHandler
    public void onArmorStandCreated(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() != EntityType.ARMOR_STAND) {
            return;
        }
        delayed(() -> checkChunk(entity.getLocation().getChunk()));
    }

    @EventHandler
    public void onArmorStandDestroyed(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() != EntityType.ARMOR_STAND) {
            return;
        }
        delayed(() -> checkChunk(entity.getLocation().getChunk()));
    }

    @EventHandler
    public void onArmorStandMaipulated(PlayerArmorStandManipulateEvent event) {
        ArmorStand stand = event.getRightClicked();
        Chunk chunk = stand.getLocation().getChunk();
        
        delayed(() -> checkChunk(chunk));
    }

    private void checkChunk(Chunk chunk) {
        List<ArmorStand> stands = Arrays.stream(chunk.getEntities())
                .filter(entity -> entity.getType() == EntityType.ARMOR_STAND)
                .map(entity -> (ArmorStand)entity)
                .collect(toList());

        for (var stand : stands) {
            if (StandChecker.SINGLETON.check(stand)) {
                // It's enough if one armor stand matches in a chunk
                KeepChunks.keepChunk(chunk);
                return;
            }
        }
        // If nothing matches we might need to release the chunk
        KeepChunks.releaseChunk(chunk);
    }

    /**
     * We have to wait a few ticks to check the armor stands, else we will see the state before player interaction
     */
    private static void delayed(Runnable toRun) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                JavaPlugin.getPlugin(ChunkyArmorStands.class),
                toRun, 10);
    }
}