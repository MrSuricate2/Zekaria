package fr.mrsuricate.zekaria.TimeIsMoney;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class runnable extends BukkitRunnable {

    public String p;
    public short tempo = 10;


    public runnable(String p) {
        this.p = p;
    }

    @Override
    public void run() {
        tempo--;
        System.out.println(tempo);
        if(tempo == 0){
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
                Main.getInstance().getTimeIsMoneyConfig().set(p,0);
            }
        }
    }
}
