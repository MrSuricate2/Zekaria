package fr.mrsuricate.zekaria.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class alert implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("alert")) {



                if (args.length == 0)
                    player.sendMessage("Il faut préciser le texte à envoyé !");
                if (args.length >= 1) {
                    StringBuilder bc = new StringBuilder();
                    byte b;
                    int i;
                    String[] arrayOfString;
                    for (i = (arrayOfString = args).length, b = 0; b < i; ) {
                        String part = arrayOfString[b];
                        bc.append(part + " ");
                        b++;
                    }
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("§8§l[§4§lZekaria§8§l]§6§l " + ChatColor.translateAlternateColorCodes('&', String.valueOf(bc)));
                    Bukkit.broadcastMessage("");
                }
                return true;
            }

        }
        return true;
    }
}