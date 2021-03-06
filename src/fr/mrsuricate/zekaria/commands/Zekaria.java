package fr.mrsuricate.zekaria.commands;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

import static org.bukkit.Bukkit.getServer;

public class Zekaria extends BukkitRunnable implements CommandExecutor {

    public static int temps = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender.hasPermission("zekaria.use")){
            if(cmd.getName().equalsIgnoreCase("zekaria")){
                if (args.length == 1){
                    if(args[0].equalsIgnoreCase("restart")){
                        sender.sendMessage("Merci de préciser dans combient de temps (en secondes)");
                    }
                }
                if (args.length == 2){
                    if(args[0].equalsIgnoreCase("restart")){
                        String tempo = args[1];
                        int tempos = 0;
                        try {
                            tempos = Integer.parseInt(tempo);
                        } catch(NumberFormatException nfe){

                        }
                        this.temps = tempos;

                    }

                }
                if(args.length == 3){
                    if(args[0].equalsIgnoreCase("potion")){
                        String name = args[2];
                        if(args[1].equalsIgnoreCase("NightVision")){
                            Main.getInstance().getpotion().set(name+".NightVision", true);
                            try {
                                Main.getInstance().getpotion().save(Main.getInstance().potion);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if(args[1].equalsIgnoreCase("Hast")){
                            Main.getInstance().getpotion().set(name+".Hast", true);
                            try {
                                Main.getInstance().getpotion().save(Main.getInstance().potion);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if(args[1].equalsIgnoreCase("speed")){
                            Main.getInstance().getpotion().set(name+".speed", true);
                            try {
                                Main.getInstance().getpotion().save(Main.getInstance().potion);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else {
            sender.sendMessage("§cDésolé, mais vous n'avez pas la permission d'exécuter cette commande !");
        }
        return false;
    }


    @Override
    public void run() {
        if(this.temps >= 0){
            this.temps --;
            if(temps == 600){
                byte b2;
                int j;
                Player[] arrayOfPlayer;
                for (j = (arrayOfPlayer = getServer().getOnlinePlayers().toArray(new Player[0])).length, b2 = 0; b2 < j; ) {
                    Player p = arrayOfPlayer[b2];
                    p.sendMessage("§dRedémarrage du serveur dans 10 minutes");
                    b2++;
                }
            }
            if(temps == 300){
                byte b2;
                int j;
                Player[] arrayOfPlayer;
                for (j = (arrayOfPlayer = getServer().getOnlinePlayers().toArray(new Player[0])).length, b2 = 0; b2 < j; ) {
                    Player p = arrayOfPlayer[b2];
                    p.sendMessage("§dRedémarrage du serveur dans 5 minutes");
                    b2++;
                }
            }
            if(temps == 60){
                byte b2;
                int j;
                Player[] arrayOfPlayer;
                for (j = (arrayOfPlayer = getServer().getOnlinePlayers().toArray(new Player[0])).length, b2 = 0; b2 < j; ) {
                    Player p = arrayOfPlayer[b2];
                    p.sendMessage("§dRedémarrage du serveur dans 1 minute");
                    b2++;
                }
            }
            if(temps == 10){
                byte b2;
                int j;
                Player[] arrayOfPlayer;
                for (j = (arrayOfPlayer = getServer().getOnlinePlayers().toArray(new Player[0])).length, b2 = 0; b2 < j; ) {
                    Player p = arrayOfPlayer[b2];
                    p.sendMessage("§dRedémarrage du serveur dans 10 secondes");
                    b2++;
                }
            }
            if(temps == 5){
                byte b2;
                int j;
                Player[] arrayOfPlayer;
                for (j = (arrayOfPlayer = getServer().getOnlinePlayers().toArray(new Player[0])).length, b2 = 0; b2 < j; ) {
                    Player p = arrayOfPlayer[b2];
                    p.sendMessage("§dRedémarrage du serveur dans 5 secondes");
                    b2++;
                }
            }
            if(temps == 3){
                byte b2;
                int j;
                Player[] arrayOfPlayer;
                for (j = (arrayOfPlayer = getServer().getOnlinePlayers().toArray(new Player[0])).length, b2 = 0; b2 < j; ) {
                    Player p = arrayOfPlayer[b2];
                    p.sendMessage("§dRedémarrage du serveur dans 3 secondes");
                    b2++;
                }
            }
            if(temps == 2){
                byte b2;
                int j;
                Player[] arrayOfPlayer;
                for (j = (arrayOfPlayer = getServer().getOnlinePlayers().toArray(new Player[0])).length, b2 = 0; b2 < j; ) {
                    Player p = arrayOfPlayer[b2];
                    p.sendMessage("§dRedémarrage du serveur dans 2 secondes");
                    b2++;
                }
            }
            if(temps == 1){
                byte b2;
                int j;
                Player[] arrayOfPlayer;
                for (j = (arrayOfPlayer = getServer().getOnlinePlayers().toArray(new Player[0])).length, b2 = 0; b2 < j; ) {
                    Player p = arrayOfPlayer[b2];
                    p.sendMessage("§dRedémarrage du serveur dans 1 seconde");
                    b2++;
                }
            }
            if(temps == 0){
                Bukkit.getServer().shutdown();
                this.cancel();
            }
        }
    }

}
