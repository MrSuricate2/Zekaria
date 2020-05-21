package fr.mrsuricate.zekaria.Décocombats;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class runnable2 extends BukkitRunnable {

    private static Player value;

    @Override
    public void run() {
        Collection onlineplayer = Bukkit.getOnlinePlayers();
        Iterator<Player> itstring = onlineplayer.iterator();
        while(itstring.hasNext()){
            this.value = itstring.next();
            if(this.value != null){
                if(Main.getInstance().getDéco_cobatsConfig().contains(value.getName())){
                    int tempr = (int) Main.getInstance().getDéco_cobatsConfig().get(value.getName());
                    if(tempr > 0){
                        tempr--;
                        Main.getInstance().getDéco_cobatsConfig().set(value.getName(), tempr);
                        try{
                            Main.getInstance().getDéco_cobatsConfig().save(Main.getInstance().Déco_Combats);
                        } catch (IOException er){
                            er.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
