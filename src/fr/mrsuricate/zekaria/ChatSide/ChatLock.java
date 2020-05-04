package fr.mrsuricate.zekaria.ChatSide;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChatLock implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if(cmd.getName().equalsIgnoreCase("chatlock")){
            if(sender.hasPermission("lockchat.use")){
                if(!Main.chatlock){
                    Bukkit.broadcastMessage("§8[§4Chat Lock§8] §6 " + sender.getName() + " §6à verrouiller le chat !");
                    Main.chatlock = true;
                } else if (Main.chatlock){
                    Bukkit.broadcastMessage("§8[§4Chat Lock§8] §6 " + sender.getName() + " §6à déverrouiller le chat !");
                    Main.chatlock = false;
                }
            } else {
                sender.sendMessage("§4Vous n'avez pas la permission !");
            }
        }
        return false;
    }
}
