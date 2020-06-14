package fr.mrsuricate.zekaria.TopFaction;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import fr.mrsuricate.zekaria.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TopFaction extends BukkitRunnable implements CommandExecutor, Listener {

    Faction faction = null;
    public static Map<String, String> name = new HashMap<>();
    public static Map<String, Double> baltop = new HashMap<>();
    public static Economy economy = null;
    public static String top1name;
    public static String top2name;
    public static String top3name;
    public static double top1;
    public static double top2;
    public static double top3;

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Main.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null)
            economy = economyProvider.getProvider();
        return (economy != null);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
            if(cmd.getName().equalsIgnoreCase("faction")){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("top")){
                        if(setupEconomy()){
                            for(OfflinePlayer offp : Bukkit.getOfflinePlayers()){
                                MPlayer mp = MPlayer.get(offp.getUniqueId());
                                if(mp != null){
                                    if(mp.hasFaction()){
                                        this.name.put(offp.getName() ,mp.getFactionName());
                                    }
                                }
                            }
                            String[] test = this.name.toString().replace("{", "").replace("}", "").split(", ");
                            int i = 0;
                            String[] namep = new String[test.length];
                            String[] namef = new String[test.length];
                            while(i != test.length){
                                int intnamep = test[i].lastIndexOf("=");
                                String t = test[i].substring(0, intnamep);
                                namep[i] = t;
                                String u = test[i].substring(intnamep+1);
                                namef[i] = u;
                                i++;
                            }
                            i = 0;

                            while(i != test.length){
                                if(!baltop.containsKey(namef[i])){
                                    baltop.put(namef[i], economy.getBalance(namep[i]));
                                } else {
                                    double money = baltop.get(namef[i]) + economy.getBalance(namep[i]);
                                    baltop.put(namef[i], money);
                                }
                                i++;
                            }
                            String[] test2 = baltop.toString().replace("{", "").replace("}", "").split(", ");
                            String[] nomf = new String[test2.length];
                            double[] moneyf = new double[test2.length];

                            i=0;
                            while(i != test2.length){
                                int intnamep = test2[i].lastIndexOf("=");
                                String t = test2[i].substring(0, intnamep);
                                nomf[i] = t;
                                String u = test2[i].substring(intnamep+1);
                                moneyf[i] = Double.parseDouble(u);
                                i++;
                            }
                            Arrays.sort(moneyf);
                            int longueru = moneyf.length;
                            this.top1 = moneyf[longueru-1];
                            this.top2 = moneyf[longueru-2];
                            this.top3 = moneyf[longueru-3];
                            i=0;
                            while(i != test2.length){
                                int intnamep = test2[i].lastIndexOf("=");
                                String faction = test2[i].substring(0, intnamep);
                                String money = test2[i].substring(intnamep+1);
                                if(Double.parseDouble(money) == top1){
                                    top1name = faction;
                                }
                                if(Double.parseDouble(money) == top2){
                                    top2name = faction;
                                }
                                if(Double.parseDouble(money) == top3){
                                    top3name = faction;
                                }
                                i++;
                            }
                            sender.sendMessage("§a§m---------§a Top 3 faction §a§m---------");
                            sender.sendMessage("");
                            sender.sendMessage("§6Top 1 : §b"+ top1name + "§6 avec §d"+ top1 + " ZekaCoins");
                            sender.sendMessage("§6Top 2 : §b"+ top2name + "§6 avec §d"+ top2 + " ZekaCoins");
                            sender.sendMessage("§6Top 3 : §b"+ top3name + "§6 avec §d"+ top3 + " ZekaCoins");
                            sender.sendMessage("");
                            sender.sendMessage("§a§m-------------------------------");
                        }
                        baltop.clear();
                    }
                }
            }
        return false;
    }

    @Override
    public void run() {
        if(setupEconomy()){
            for(OfflinePlayer offp : Bukkit.getOfflinePlayers()){
                MPlayer mp = MPlayer.get(offp.getUniqueId());
                if(mp != null){
                    if(mp.hasFaction()){
                        this.name.put(offp.getName() ,mp.getFactionName());
                    }
                }
            }
            String[] test = this.name.toString().replace("{", "").replace("}", "").split(", ");
            int i = 0;
            String[] namep = new String[test.length];
            String[] namef = new String[test.length];
            while(i != test.length){
                int intnamep = test[i].lastIndexOf("=");
                String t = test[i].substring(0, intnamep);
                namep[i] = t;
                String u = test[i].substring(intnamep+1);
                namef[i] = u;
                i++;
            }
            i = 0;

            while(i != test.length){
                if(!baltop.containsKey(namef[i])){
                    baltop.put(namef[i], economy.getBalance(namep[i]));
                } else {
                    double money = baltop.get(namef[i]) + economy.getBalance(namep[i]);
                    baltop.put(namef[i], money);
                }
                i++;
            }
            String[] test2 = baltop.toString().replace("{", "").replace("}", "").split(", ");
            String[] nomf = new String[test2.length];
            double[] moneyf = new double[test2.length];

            i=0;
            while(i != test2.length){
                int intnamep = test2[i].lastIndexOf("=");
                String t = test2[i].substring(0, intnamep);
                nomf[i] = t;
                String u = test2[i].substring(intnamep+1);
                moneyf[i] = Double.parseDouble(u);
                i++;
            }
            Arrays.sort(moneyf);
            int longueru = moneyf.length;
            this.top1 = moneyf[longueru-1];
            this.top2 = moneyf[longueru-2];
            this.top3 = moneyf[longueru-3];
            i=0;
            while(i != test2.length){
                int intnamep = test2[i].lastIndexOf("=");
                String faction = test2[i].substring(0, intnamep);
                String money = test2[i].substring(intnamep+1);

                if(Double.parseDouble(money) == top1){
                    top1name = faction;
                }
                if(Double.parseDouble(money) == top2){
                    top2name = faction;
                }
                if(Double.parseDouble(money) == top3){
                    top3name = faction;
                }
                i++;
            }
        }
        baltop.clear();
        Plugin plugin = Main.getInstance();
        Location where = new Location(Bukkit.getWorld("world"), 171.5,76,234.5);
        Hologram hologram = HologramsAPI.createHologram(plugin,where);
        TextLine textLine0 = hologram.insertTextLine(0 ,"§a§m---------§a Top 3 faction §a§m---------" );
        TextLine textLine1 = hologram.insertTextLine(1 ,"" );
        TextLine textLine2 = hologram.insertTextLine(2 ,"§6Top 1 : §b"+ this.top1name + "§6 avec §d"+ this.top1 + " ZekaCoins" );
        TextLine textLine3 = hologram.insertTextLine(3 ,"§6Top 2 : §b"+ this.top2name + "§6 avec §d"+ this.top2 + " ZekaCoins" );
        TextLine textLine4 = hologram.insertTextLine(4 ,"§6Top 3 : §b"+ this.top3name + "§6 avec §d"+ this.top3 + " ZekaCoins" );
        TextLine textLine5 = hologram.insertTextLine(5 ,"" );
        TextLine textLine6 = hologram.insertTextLine(6 ,"§a§m-------------------------------" );
        for (Hologram holograms : HologramsAPI.getHolograms(plugin)) {
            deleteIfOld(holograms);
        }
    }
    public void deleteIfOld(Hologram hologram) {

        long tenMinutesMillis = 200; // Ten minutes in milliseconds
        long elapsedMillis = System.currentTimeMillis() - hologram.getCreationTimestamp(); // Milliseconds elapsed from the creation of the hologram

        if (elapsedMillis > tenMinutesMillis) {
            hologram.delete();
        }
    }
}
