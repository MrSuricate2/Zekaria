package fr.mrsuricate.zekaria.ChatSide;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if(cmd.getName().equalsIgnoreCase("clearchat")){
            if(sender instanceof Player){
                if(sender.hasPermission("clearchat.use")){
                    for (int i = 0; i < 100; i++){
                        Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(""));
                    }
                } else {
                    sender.sendMessage("§4Vous n'avez pas la permission !");
                }
                Bukkit.broadcastMessage("§8[§4Clear Chat§8] §6 Le chat à été réinitialisé !");
                return true;
            }
            return false;

        }

        if (cmd.getName().equalsIgnoreCase("localclear")){
            if(sender instanceof Player){
                if(sender.hasPermission("localclear.use")){
                    for (int i = 0; i < 100; i++){
                        sender.sendMessage("");
                    }
                } else {
                    sender.sendMessage("§4Vous n'avez pas la permission !");
                }
                sender.sendMessage("§8[§4Clear Chat§8] §6 Votre chat a été réinitialisé !");
                return true;
            }
            return false;
        }
        return false;
    }
}
