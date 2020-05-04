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
            if(!(sender instanceof Player)){
                sender.sendMessage("Vous devez être en jeu pour éxécuter cette commande");
            }
            if(sender.hasPermission("clearchat.use")){
                for (int i = 0; i < 100; i++){
                    Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(""));
                }
            } else {
                sender.sendMessage("§4Vous n'avez pas la permission !");
            }
            Bukkit.broadcastMessage("§8[§4Clear Chat§8] §6 Le chat à été réinitialisé !");
        }

        return false;
    }
}