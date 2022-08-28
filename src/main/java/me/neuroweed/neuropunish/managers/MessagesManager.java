package me.neuroweed.neuropunish.managers;

import me.neuroweed.neuropunish.Main;
import org.bukkit.ChatColor;
public class MessagesManager {
    static Main plugin;
    public MessagesManager(Main instance) {
        plugin = instance;
    }

    public static String Space;
    public static String Author;
    public static String GithubAuthorLink;
    public static String AuthorColored;
    public static String OnlyPlayersCanExecute;
    public static String NotAllowedHere;

    public static String c (String txt) {
        return ChatColor.translateAlternateColorCodes('&', txt);
    }
    public MessagesManager() {
        Space = " ";
        Author = "neuroweed420";
        GithubAuthorLink = "https://github.com/matiasweb";
        AuthorColored = ChatColor.RED + Author;
        NotAllowedHere = ChatColor.RED + c("The command is not allowed here");
        OnlyPlayersCanExecute = ChatColor.RED + c("Only players can execute this command!");
    }


}