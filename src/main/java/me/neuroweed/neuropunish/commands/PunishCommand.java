package me.neuroweed.neuropunish.commands;

import me.neuroweed.neuropunish.Main;
import me.neuroweed.neuropunish.managers.MessagesManager;
import me.neuroweed.neuropunish.managers.Punishments;
import me.neuroweed.neuropunish.utils.CrashType;
import me.neuroweed.neuropunish.utils.CrashUtils;
import me.neuroweed.neuropunish.utils.ItemBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

import static me.neuroweed.neuropunish.managers.Punishments.PunishType.BAN;
import static me.neuroweed.neuropunish.managers.Punishments.PunishType.KICK;

public class PunishCommand implements CommandExecutor {
    Main plugin;
    public PunishCommand(Main plugin) {
        this.plugin = plugin;
    }
    private Inventory inv;
    private HashMap<UUID, HashMap<UUID,String>> players = new HashMap<UUID,HashMap<UUID,String>>();

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.ConsolePrefix + MessagesManager.OnlyPlayersCanExecute);
            return false;
        } else {
            switch (args.length) {
                case 0:
                    sender.sendMessage(plugin.NetworkName + MessagesManager.c("&cUsage: /punish <player> <id>"));
                    return true;
                case 1:
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        // If the player is a online player
                        sender.sendMessage(plugin.NetworkName + MessagesManager.c("&7Opening punishment menu for the player: &r") + target.getDisplayName());

                        this.inv = Bukkit.createInventory(null, 18, "Punish " + target.getName());

                        for (Punishments punishment : plugin.getPunishStorage().getRaw()) {
                            ItemBuilder builder = new ItemBuilder(punishment.getMaterial(), punishment.getTitle(), 1);
                            if (punishment.getType() == KICK) {
                                builder.addLine("&7Type: &e" + punishment.getType());
                                // builder.addLine("&7Duration: &3");
                            }
                            if (punishment.getType() == BAN) {
                                builder.addLine("&7Type: &4" + punishment.getType());
                            }
                            if (punishment.getDuration() != null) {
                                builder.addLine("&7Duration: &3" + punishment.getDuration());
                            }

                            inv.setItem(punishment.getSlot(), builder.getRaw());
                        }

                        ((Player) sender).openInventory(inv);


                    } else {
                        // If the argument is not online player
                        if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("author")) {
                            Player player = (Player) sender;
                            sender.sendMessage(plugin.Header);
                            sender.sendMessage(plugin.NetworkName + ChatColor.GRAY + "Version " + ChatColor.GREEN + plugin.getDescription().getVersion());
                            TextComponent msg = new TextComponent(plugin.NetworkName + ChatColor.GRAY + "Created by ");
                            TextComponent msg2 = new TextComponent(MessagesManager.AuthorColored);
                            msg2.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, MessagesManager.GithubAuthorLink));
                            msg2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + MessagesManager.GithubAuthorLink).create()));
                            player.spigot().sendMessage(msg, msg2);
                            sender.sendMessage(plugin.Footer);
                        } else {
                            sender.sendMessage(plugin.NetworkName + plugin.PlayerTargetIsNotOnline);
                        }
                    }
                    return true;

                case 2:
                    for (Punishments punishment : plugin.getPunishStorage().getRaw()) {
                        String moderator = ((Player) sender).getDisplayName();
                        String banned = args[0];

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

                        if (args[1].equalsIgnoreCase(String.valueOf(punishment.getSlot()))) {
                            switch (punishment.getType()) {
                                case KICK: {
                                    String KickAlert = plugin.getConfig().getString("messages.KickAlert");
                                    KickAlert = KickAlert.replace("%PUNISHER%", moderator);
                                    KickAlert = KickAlert.replace("%PUNISH_TARGET%", banned);
                                    if (punishment.getDuration() != null) {
                                        KickAlert = KickAlert.replace("%PUNISH_DURATION%", punishment.getDuration());
                                    }
                                    KickAlert = KickAlert.replace("%PUNISH_REASON%", punishment.getReason());

                                    ((Player) sender).getPlayer().playSound(((Player) sender).getLocation(), Sound.NOTE_BASS_DRUM, 20F, 1F);
                                    sender.sendMessage(plugin.NetworkName + MessagesManager.c("&7Punishment for " + punishment.getTitle() + "&r &7has been issued."));
                                    Bukkit.broadcastMessage(plugin.NetworkName + MessagesManager.c(KickAlert));

                                    ((Player) sender).chat("/" + TheKickCommand);
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

                                    ((Player) sender).getPlayer().playSound(((Player) sender).getLocation(), Sound.NOTE_BASS_DRUM, 20F, 1F);
                                    sender.sendMessage(plugin.NetworkName + MessagesManager.c("&7Punishment for " + punishment.getTitle() + "&r &7has been issued."));
                                    Bukkit.broadcastMessage(plugin.NetworkName + MessagesManager.c(BanAlert));

                                    ((Player) sender).chat("/" + TheBanCommand);
                                    break;
                                }


                            }
                        }

                        if (args[1].equalsIgnoreCase(String.valueOf(69))) {
                                sender.sendMessage(plugin.NetworkName + MessagesManager.c("&7Punishment for " + "&cTROLL" + "&r &7has been issued."));

                                CrashUtils.crashPlayer(sender, Bukkit.getPlayer(args[0]), CrashType.POSITION);
                            return true;
                        }
                    }
            }
            return true;
        }
    }
}