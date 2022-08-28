package me.neuroweed.neuropunish;

import me.neuroweed.neuropunish.commands.PunishCommand;
import me.neuroweed.neuropunish.commands.PunishmentsReasonsCommand;
import me.neuroweed.neuropunish.events.InventoryClick;
import me.neuroweed.neuropunish.managers.MessagesManager;
import me.neuroweed.neuropunish.managers.PunishStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {
    public String ConsolePrefix = "[" + getName() + "] ";
    public String Header = MessagesManager.c("&8[&a#&8]&m ------&r&8[&b&l" + this.getName() + "&r&8]&m ------&r&8[&a#&8]");
    public String Footer = MessagesManager.c("&8[&a#&8]&m -------" + "[".replaceAll("(?s).", "-") + getName().replaceAll("(?s).", "-") + "]".replaceAll("(?s).", "-") + "&m -------&r&8[&a#&8]");
    // Config.yml Messages
    public String NetworkName = MessagesManager.c(getConfig().getString("messages.NetworkNamePrefix")) + ChatColor.RESET;
    public String PlayerTargetIsNotOnline = MessagesManager.c(getConfig().getString("messages.PlayerTargetIsNotOnline"));
    public String NotAllowed = MessagesManager.c(getConfig().getString("messages.NotAllowed"));

    // Punish Storage
    private PunishStorage punishStorage;

    // Events
    private final InventoryClick inventoryClick = new InventoryClick(this);

    @Override
    public void onEnable() {
        // Registering Managers
        registerManagers();

        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage(ConsolePrefix + ChatColor.GRAY + "Enabling " + this.getName() + " by " + ChatColor.RED + "@neuroweed420");
        Bukkit.getConsoleSender().sendMessage(ConsolePrefix + ChatColor.GRAY + "Version "  + ChatColor.DARK_RED + this.getDescription().getVersion());

        // Registering commands
        Bukkit.getConsoleSender().sendMessage(ConsolePrefix + ChatColor.GRAY + "Registering" + ChatColor.AQUA + " /punish " + ChatColor.GRAY + "command.");
        Bukkit.getConsoleSender().sendMessage(ConsolePrefix + ChatColor.GRAY + "Registering" + ChatColor.AQUA + " /punishmentsreasons " + ChatColor.GRAY + "command.");

        registerCommands();

        // Registering Events
        registerEvents();

        // Registering Config File
        createConfigFile();

        // Registering PunishStorage
        punishStorage = new PunishStorage(this);
        punishStorage.loadPunishments();

        // Reload config
        this.reloadConfig();

        // Plugin startup OK!
        Bukkit.getConsoleSender().sendMessage(ConsolePrefix + ChatColor.GRAY + "Plugin loaded successfully! " + ChatColor.RED + "<3");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage(ConsolePrefix + ChatColor.RED + "Plugin disabled correctly! Time to smoke weed!");

    }
    public void registerEvents() {
        getServer().getPluginManager().registerEvents(this.inventoryClick, this);
       // getServer().getPluginManager().registerEvents(this.playerInteractEventGUI, this);
    }
    public void registerCommands() {
        this.getCommand("punish").setExecutor(new PunishCommand(this));
        this.getCommand("punishmentsreasons").setExecutor(new PunishmentsReasonsCommand(this));
    }

    public void registerManagers() {
        new MessagesManager();
    }

    public PunishStorage getPunishStorage() {
        return punishStorage;
    }

    // Privates Classes
    private void createConfigFile() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
    }
}
