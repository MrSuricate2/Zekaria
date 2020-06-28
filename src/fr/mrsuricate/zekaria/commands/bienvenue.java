package fr.mrsuricate.zekaria.commands;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.bukkit.Bukkit.getServer;

public class bienvenue extends BukkitRunnable implements CommandExecutor, Listener {

    public static byte b2;
    public static int j;
    public static Player[] arrayOfPlayer;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            if (cmd.getName().equalsIgnoreCase("bienvenue")) {
                if (args.length == 1){
                    String name = args[0];
                    if(sender.getName() != name){
                        if (Main.getInstance().getNewPlayer().contains(name)){
                            int timer = Main.getInstance().getNewPlayer().getInt(name+".timer");
                            if(timer != 0){
                                if(!Main.getInstance().getNewPlayer().contains(name+"."+sender.getName())){
                                    Player p = Bukkit.getServer().getPlayer(name);
                                    String send = ((Player) sender).getDisplayName();
                                    p.sendMessage(send + " §&Vous souhaite la bienvenue sur Zekaria !");
                                    sender.sendMessage("Vous venez de souhaiter la bienvenue à "+ name);
                                    Main.getInstance().getNewPlayer().set(name+"."+sender.getName(),true);
                                    try {
                                        Main.getInstance().getNewPlayer().save(Main.getInstance().NewPlayer);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                } else {
                                    sender.sendMessage("§aVous avez déjà souhaité la bienvenue à ce joueur !");
                                }
                            } else {
                                sender.sendMessage("§aCe joueur n'est pas nouveaux");
                            }
                        } else {
                            sender.sendMessage("§cCe joueur ne c'est jamais connecter");
                        }
                    } else {
                        sender.sendMessage("§cVous ne pouvez pas vous souhaiter la bienvenue à vous même");
                    }
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if(!Main.getInstance().getNewPlayer().contains(player.getName())){
            Main.getInstance().getNewPlayer().set(player.getName()+".dateofjoin",LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            Main.getInstance().getNewPlayer().set(player.getName()+".timer",30);
            try {
                Main.getInstance().getNewPlayer().save(Main.getInstance().NewPlayer);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        for (j = (arrayOfPlayer = getServer().getOnlinePlayers().toArray(new Player[0])).length, b2 = 0; b2 < j; ) {
            Player p = arrayOfPlayer[b2];
            String name = p.getName();
            int i = Main.getInstance().getNewPlayer().getInt(name+".timer");
            if(i != 0){
                i--;
                Main.getInstance().getNewPlayer().set(name+".timer",i);
                try {
                    Main.getInstance().getNewPlayer().save(Main.getInstance().NewPlayer);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            b2++;
        }
    }
}
