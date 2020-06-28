package fr.mrsuricate.zekaria.moderation;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onQUit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(Main.getInstance().modlist.containsKey(p.getName())){
            new PlayerManager(p).destroy();
            Main.getInstance().modlist.remove(p.getName());
            float satu = Main.getInstance().saturation.get(p.getName());
            int food = Main.getInstance().food.get(p.getName());
            double heal = Main.getInstance().heal.get(p.getName());
            p.setFoodLevel(food);
            p.setSaturation(satu);
            p.setHealth(heal);
            Main.getInstance().saturation.remove(p.getName());
            Main.getInstance().food.remove(p.getName());
            Main.getInstance().heal.remove(p.getName());
        }
    }
}
