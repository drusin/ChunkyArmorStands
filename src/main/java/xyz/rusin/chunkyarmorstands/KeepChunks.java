package xyz.rusin.chunkyarmorstands;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

/**
 * Helper class for communication with KeepChunks via console commands
 */
public class KeepChunks {

    public static boolean keepChunk(Chunk chunk) {
        String command = String.format("keepchunks keepchunk coords %d %d %s", chunk.getX(), chunk.getZ(), chunk.getWorld().getName());
        return Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static boolean releaseChunk(Chunk chunk) {
        String command = String.format("keepchunks releasechunk coords %d %d %s", chunk.getX(), chunk.getZ(), chunk.getWorld().getName());
        return Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}