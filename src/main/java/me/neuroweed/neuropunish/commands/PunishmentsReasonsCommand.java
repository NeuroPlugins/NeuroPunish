package me.neuroweed.neuropunish.commands;

import me.neuroweed.neuropunish.Main;
import me.neuroweed.neuropunish.managers.MessagesManager;
import me.neuroweed.neuropunish.managers.Punishments;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishmentsReasonsCommand implements CommandExecutor {
    Main plugin;
    public PunishmentsReasonsCommand(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.ConsolePrefix + MessagesManager.OnlyPlayersCanExecute);
            return false;
        } else {
            if (args.length == 0) {
                sender.sendMessage(plugin.Header);
                sender.sendMessage(MessagesManager.c("&7Punishments reasons:&r"));

                for (Punishments punishment : plugin.getPunishStorage().getRaw()) {
                    sender.sendMessage(MessagesManager.c("&7ID " + punishment.getSlot() + ": &3" + punishment.getReason()));
                }

                sender.sendMessage(plugin.Footer);
                return true;
            }
            return true;
        }
    }
}