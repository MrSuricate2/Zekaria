package fr.mrsuricate.zekaria.StaffChat;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StaffChat implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player && sender.hasPermission("staffchat.use")){
            if (cmd.getName().equalsIgnoreCase("staffchat")){
                if(args.length == 0){
                    sender.sendMessage("§6/sc §aon§7/§coff");
                }
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("on")){
                        sender.sendMessage("§4Vous entrez dans le StaffChat");
                        Main.getInstance().map.put(sender.getName(), 1);
                    }
                    if(args[0].equalsIgnoreCase("off")){
                        sender.sendMessage("§4Vous quittez le StaffChat");
                        Main.getInstance().map.put(sender.getName(), 0);
                    }
                }
            }
        }
        return false;
    }


    @EventHandler
    public void onChatStaff(AsyncPlayerChatEvent e){
        String playername = e.getPlayer().getName();
        if(e.getPlayer().hasPermission("staffchat.use")){
            if(Main.getInstance().map.containsKey(playername)){
                int key = Main.getInstance().map.get(playername);
                if(key == 1){
                    e.setCancelled(true);
                    for(Player player : Bukkit.getOnlinePlayers()){
                        if(player.hasPermission("staffchat.use")){
                            player.sendMessage("§8[§4Staff Chat§8] " + e.getPlayer().getDisplayName() + ": §6" + e.getMessage());
                        }
                    }
                }
            }
        }


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        String Name = e.getPlayer().getName();
        Main.getInstance().map.put(Name, 0);
    }




}