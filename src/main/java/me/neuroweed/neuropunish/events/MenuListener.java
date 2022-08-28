package me.neuroweed.neuropunish.events;

import lombok.RequiredArgsConstructor;
import me.neuroweed.neuropunish.Main;
import me.neuroweed.neuropunish.managers.Punishments;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

@RequiredArgsConstructor
public class MenuListener implements Listener {
    private final Main plugin;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        int clicked = event.getRawSlot();
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory() == null || event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || event.getClickedInventory().getTitle().contains("Punish")) {
            return;
        }

        event.setCancelled(true);

        String name =  event.getClickedInventory().getTitle().replace("Punish ", "");
        for (Punishments punishment : plugin.getPunishStorage().getRaw()) {
            if (clicked == punishment.getSlot()) {
                switch (punishment.getType()) {
                    case BAN: {
                        player.playSound(event.getWhoClicked().getLocation(), Sound.NOTE_BASS_DRUM, 20F, 1F);
                        player.chat("/kick " + name + " " + punishment.getReason());
                        break;
                    }
                    case KICK: {
                        player.playSound(event.getWhoClicked().getLocation(), Sound.NOTE_BASS_DRUM, 20F, 1F);
                        player.chat("/ban " + name + " " + punishment.getDuration() + " " + punishment.getReason());
                        break;
                    }
                }
            }
        }
    }
}