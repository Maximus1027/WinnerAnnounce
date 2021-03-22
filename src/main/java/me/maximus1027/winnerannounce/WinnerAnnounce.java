package me.maximus1027.winnerannounce;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WinnerAnnounce extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        getCommand("winnerannounce").setExecutor(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

    }


    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("winnerannounce.use")) {
            sender.sendMessage(ChatColor.RED + "Insufficient Permissions.");
            return false;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /ka <title/subtitle> <new title/subtitle>");
            sender.sendMessage(ChatColor.RED + "Example: /ka title &6%player% | /ka subtitle &aWon the %event%!");
            sender.sendMessage(ChatColor.GRAY + "--");
            sender.sendMessage(ChatColor.RED + "Usage: /ka <player> <event-name>");
            return false;
        }

        if (!(args[0].equalsIgnoreCase("title")) && !(args[0].equalsIgnoreCase("subtitle"))) {

            String event = args.length < 3 ? args[1] : args[1] + " " + args[2];

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("title").replaceAll("%player%", args[0]).replaceAll("%event%", event)), ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("subtitle").replaceAll("%player%", args[0]).replaceAll("%event%", event)));
            }

            sender.sendMessage(ChatColor.GREEN + "Successfully sent title and subtitle");
            return false;
        } else if (args[0].equalsIgnoreCase("title") || args[0].equalsIgnoreCase("subtitle")) {
            String newConfig = "";
            for (String word : args) {
                newConfig += word + " ";
            }
            newConfig = newConfig.replaceFirst(args[0], "").replaceFirst(" ", "");


            this.getConfig().set(args[0], newConfig);
            this.saveConfig();

            sender.sendMessage(ChatColor.GREEN + "Set " + args[0] + " to " + newConfig);
            return false;
        }
        return false;
    }
}
