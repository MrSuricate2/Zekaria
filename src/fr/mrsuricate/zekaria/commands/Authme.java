package fr.mrsuricate.zekaria.commands;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class Authme implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player){
            if(sender.hasPermission("chatformat.developeur") || sender.hasPermission("chatformat.administrateur") || sender.hasPermission("chatformat.fondateur") || sender.hasPermission("chatformat.sysadmin")){
                if (cmd.getName().equalsIgnoreCase("jarvis")) {
                    if (args.length == 2){
                        if(!Main.getInstance().getauthme().contains(sender.getName())){
                            if (args[0].equalsIgnoreCase("register")){
                                if(!Main.getInstance().getauthme().getBoolean(sender.getName()+".register")){
                                    if(args[1].length() >= 10){
                                        Main.getInstance().getauthme().set(sender.getName()+".mdp", args[1]);
                                        Main.getInstance().getauthme().set(sender.getName()+".register", true);
                                        try {
                                            Main.getInstance().getauthme().save(Main.getInstance().authme);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        sender.sendMessage("&aVotre mots de passe à était saisie avec succès");
                                    } else {
                                        sender.sendMessage("&aMots de passe trop court (10 caractère min)");
                                    }
                                }
                            }
                        }
                        if (args[0].equalsIgnoreCase("login")){
                            String mdp = Main.getInstance().getauthme().getString(sender.getName()+".mdp");
                            if(args[1].equalsIgnoreCase(mdp)){
                                sender.setOp(true);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if(e.getPlayer().isOp()){
            e.getPlayer().setOp(false);
        }
    }


}
