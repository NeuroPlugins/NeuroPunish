package me.neuroweed.neuropunish.managers;

import me.neuroweed.neuropunish.Main;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class PunishStorage {
    private final Main plugin;
    private final List<Punishments> storage;

    public PunishStorage(Main plugin) {
        this.plugin = plugin;
        this.storage = new ArrayList<>();
    }

    public Punishments getPunishment(String key) {
        for (Punishments punishments : storage) {
            if (punishments.getKey().equals(key))
                return punishments;
        }

        return null;
    }

    public void remove(String key) {
        for (Punishments punishments : storage) {
            if (punishments.getKey().equals(key)) {
                storage.remove(punishments);
                break;
            }
        }
    }

    public void add(Punishments punishments) {
        storage.add(punishments);
    }

    public void loadPunishments() {

        plugin.getConfig().getConfigurationSection("punish-id").getKeys(false).forEach(key -> {

            Punishments punishments = new Punishments();
            punishments.setKey(key);
            punishments.setSlot(plugin.getConfig().getInt("punish-id."  + key + ".SLOT_GUI"));
            punishments.setType(Punishments.PunishType.valueOf(plugin.getConfig().getString("punish-id." + key + ".PUNISH_TYPE")));
            punishments.setMaterial(Material.valueOf(plugin.getConfig().getString("punish-id." + key + ".PUNISH_MATERIAL")));
            punishments.setTitle(MessagesManager.c(plugin.getConfig().getString("punish-id." + key + ".PUNISH_TITLE")));
            punishments.setReason(MessagesManager.c(plugin.getConfig().getString("punish-id." + key + ".PUNISH_REASON")));

            if(plugin.getConfig().getConfigurationSection("punish-id." + key).contains("PUNISH_DURATION")) {
                punishments.setDuration(plugin.getConfig().getString("punish-id."  + key + ".PUNISH_DURATION"));
            }

            storage.add(punishments);
        });

    }

    public List<Punishments> getRaw() {
        return storage;
    }
}
