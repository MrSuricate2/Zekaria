package fr.mrsuricate.zekaria.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class discord implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("discord")) {
                player.sendMessage("");
                player.sendMessage("§4§lTest");
                player.sendMessage("");
                return true;
            }
        }
        return true;
    }
}
