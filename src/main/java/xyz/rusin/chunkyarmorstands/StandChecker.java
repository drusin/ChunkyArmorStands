package xyz.rusin.chunkyarmorstands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public enum StandChecker {
    SINGLETON;
    
    private final Map<EquipmentSlot, Material> mapping = new HashMap<>();

    public void loadConfig() {
        var config = JavaPlugin.getPlugin(ChunkyArmorStands.class).configuration;
        mapping.clear();
        for (String key: config.getKeys(false)) {
            mapping.put(EquipmentSlot.valueOf(key), Material.valueOf(config.getString(key)));
        }
    }

    public boolean check(ArmorStand stand) {
        return mapping.entrySet().stream()
            .map(entry -> stand.getEquipment().getItem(entry.getKey()).getType() == entry.getValue())
            .allMatch(entry -> entry == true);
    }
}
