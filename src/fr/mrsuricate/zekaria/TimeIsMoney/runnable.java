package fr.mrsuricate.zekaria.TimeIsMoney;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class runnable extends BukkitRunnable {

    public String p;
    public Player player;
    public short tempo = 10;


    public runnable(String p, Player player) {
        this.p = p;
        this.player = player;
    }

    @Override
    public void run() {
        tempo--;
        if(tempo == 0){
            if(Main.getInstance().onlinePlayer.containsKey(p)){
                int temps = (int) Main.getInstance().getTimeIsMoneyConfig().get(p);
                temps++;
                Main.getInstance().getTimeIsMoneyConfig().set(p,temps);
                tempo = 10;
                try{
                    Main.getInstance().getTimeIsMoneyConfig().save(Main.getInstance().TimeIsMoney);
                } catch (IOException er){
                    er.printStackTrace();
                }
                if(Main.getInstance().getTimeIsMoneyConfig().get(p).equals(360)){
                    Main.getEconomy().depositPlayer(p,150);
                    player.sendMessage("§aVous venez de reçevoir §b150$ §apour votre présence sur le serveur !");
                    Main.getInstance().getTimeIsMoneyConfig().set(p,0);
                }
            } else {
                this.cancel();
            }

        }
    }
}
