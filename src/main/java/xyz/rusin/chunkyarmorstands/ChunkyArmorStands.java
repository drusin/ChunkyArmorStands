package xyz.rusin.chunkyarmorstands;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public class ChunkyArmorStands extends JavaPlugin {

    private File configurationFile = new File(getDataFolder(), "config.yml");
    private FileConfiguration configuration = YamlConfiguration.loadConfiguration(configurationFile);

    @Override
    public void onEnable() {
        ensureConfig();
        StandChecker.SINGLETON.loadConfig();
        getServer().getPluginManager().registerEvents(new ArmorStandListener(), this);
        
        Commands commands = new Commands();
        getCommand(Commands.COMMAND).setExecutor(commands);
        getCommand(Commands.COMMAND).setTabCompleter(commands);
    }

    private void ensureConfig() {
        if (configuration.getKeys(false).isEmpty()) {
            configuration.set(EquipmentSlot.HEAD.name(), Material.IRON_HELMET.name());
            configuration.set(EquipmentSlot.CHEST.name(), Material.IRON_CHESTPLATE.name());
            configuration.set(EquipmentSlot.LEGS.name(), Material.IRON_LEGGINGS.name());
            configuration.set(EquipmentSlot.FEET.name(), Material.IRON_BOOTS.name());
            try {
                configuration.save(configurationFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Configuration reloadAndGetConfiguration() {
        try {
            configuration.load(configurationFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getConfiguration();
    }
}
