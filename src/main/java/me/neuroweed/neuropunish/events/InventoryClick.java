package me.neuroweed.neuropunish.events;

import me.neuroweed.neuropunish.Main;
import me.neuroweed.neuropunish.managers.MessagesManager;
import me.neuroweed.neuropunish.managers.Punishments;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class InventoryClick implements Listener {
    Main plugin;
    public InventoryClick(Main instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        int clicked = event.getRawSlot();
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory() == null || event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if(event.getClick() == ClickType.NUMBER_KEY) {
            event.setCancelled(true);
        }

        if(event.getWhoClicked() instanceof Player) {
            ArrayList<ItemStack> items = new ArrayList<>();
            items.add(event.getCurrentItem());
            items.add(event.getCursor());
            items.add((event.getClick() == ClickType.NUMBER_KEY) ? event.getWhoClicked().getInventory().getItem(event.getHotbarButton()) : event.getCurrentItem());
            event.setCancelled(true);
        }

        String name_banned =  event.getClickedInventory().getTitle().replace("Punish ", "");
        String banned = Bukkit.getPlayer(name_banned).getDisplayName();
        String moderator = Bukkit.getPlayer(event.getWhoClicked().getName()).getDisplayName();


        for (Punishments punishment : plugin.getPunishStorage().getRaw()) {

            String TheKickCommand = plugin.getConfig().getString("kick-command");
            TheKickCommand = TheKickCommand.replace("%PUNISHER%", moderator);
            TheKickCommand = TheKickCommand.replace("%PUNISH_TARGET%", banned);
            if (punishment.getDuration() != null) {
                TheKickCommand = TheKickCommand.replace("%PUNISH_DURATION%", punishment.getDuration());
            }
            TheKickCommand = TheKickCommand.replace("%PUNISH_REASON%", punishment.getReason());

            String TheBanCommand = plugin.getConfig().getString("ban-command");
            TheBanCommand = TheBanCommand.replace("%PUNISHER%", moderator);
            TheBanCommand = TheBanCommand.replace("%PUNISH_TARGET%", banned);
            if (punishment.getDuration() != null) {
                TheBanCommand = TheBanCommand.replace("%PUNISH_DURATION%", punishment.getDuration());
            }
            TheBanCommand = TheBanCommand.replace("%PUNISH_REASON%", punishment.getReason());

            if (clicked == punishment.getSlot()) {
                switch (punishment.getType()) {
                    case KICK: {
                        String KickAlert = plugin.getConfig().getString("messages.KickAlert");
                        KickAlert = KickAlert.replace("%PUNISHER%", moderator);
                        KickAlert = KickAlert.replace("%PUNISH_TARGET%", banned);
                        if (punishment.getDuration() != null) {
                            KickAlert = KickAlert.replace("%PUNISH_DURATION%", punishment.getDuration());
                        }
                        KickAlert = KickAlert.replace("%PUNISH_REASON%", punishment.getReason());

                        player.playSound(event.getWhoClicked().getLocation(), Sound.NOTE_BASS_DRUM, 20F, 1F);
                        player.sendMessage(plugin.NetworkName + MessagesManager.c("&7Punishment for " + punishment.getTitle() + "&r &7has been issued."));
                        Bukkit.broadcastMessage(plugin.NetworkName + MessagesManager.c(KickAlert));

                        player.chat("/" + TheKickCommand);

                        player.closeInventory();
                        break;
                    }
                    case BAN: {
                        String BanAlert = plugin.getConfig().getString("messages.BanAlert");
                        BanAlert = BanAlert.replace("%PUNISHER%", moderator);
                        BanAlert = BanAlert.replace("%PUNISH_TARGET%", banned);
                        if (punishment.getDuration() != null) {
                            BanAlert = BanAlert.replace("%PUNISH_DURATION%", punishment.getDuration());
                        }
                        BanAlert = BanAlert.replace("%PUNISH_REASON%", punishment.getReason());

                        player.playSound(event.getWhoClicked().getLocation(), Sound.NOTE_BASS_DRUM, 20F, 1F);
                        player.sendMessage(plugin.NetworkName + MessagesManager.c("&7Punishment for " + punishment.getTitle() + "&r &7has been issued."));
                        Bukkit.broadcastMessage(plugin.NetworkName + MessagesManager.c(BanAlert));

                        player.chat("/" + TheBanCommand);

                        player.closeInventory();
                        break;
                    }
                }
            }
        }
    }
}