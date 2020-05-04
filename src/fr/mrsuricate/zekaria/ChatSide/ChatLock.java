package fr.mrsuricate.zekaria.ChatSide;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatLock implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if(cmd.getName().equalsIgnoreCase("chatlock")){
            if(sender.hasPermission("lockchat.use")){
                if(!Main.chatlock){
                    Bukkit.broadcastMessage("§8[§4Chat Lock§8] §6 " + sender.getName() + " §6a verrouiller le chat !");
                    Main.chatlock = true;
                } else if (Main.chatlock){
                    Bukkit.broadcastMessage("§8[§4Chat Lock§8] §6 " + sender.getName() + " §6a déverrouiller le chat !");
                    Main.chatlock = false;
                }
            } else {
                sender.sendMessage("§4Vous n'avez pas la permission !");
            }
        }
        return false;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(Main.chatlock && !e.getPlayer().hasPermission("cl.bypass")){
            e.setCancelled(true);
            Bukkit.broadcastMessage("§8[§4Lock Chat§8] §6Le chat a été désactiver");
        }
    }

}
