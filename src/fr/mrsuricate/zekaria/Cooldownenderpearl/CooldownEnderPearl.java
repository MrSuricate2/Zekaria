package fr.mrsuricate.zekaria.Cooldownenderpearl;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class CooldownEnderPearl extends BukkitRunnable implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        String name = e.getPlayer().getName();
        if(!Main.getInstance().getCenderPearl().contains(name)){
            Main.getInstance().getCenderPearl().set(name,0);
            try {
                Main.getInstance().getCenderPearl().save(Main.getInstance().Cenderpearl);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        String name = e.getPlayer().getName();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getItem() != null){
                if(e.getItem().getType() == Material.ENDER_PEARL ){
                    if(Main.getInstance().getCenderPearl().get(name).equals(0)){
                        Main.getInstance().getCenderPearl().set(e.getPlayer().getName(),10);
                        try {
                            Main.getInstance().getCenderPearl().save(Main.getInstance().Cenderpearl);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else {
                        e.setCancelled(true);
                        int a = Main.getInstance().getCenderPearl().getInt(name);
                        e.getPlayer().sendMessage("§cVeuillez attendre §b"+ a + "§b secondes §cavant de pouvoir relancer une enderpearl !");
                        e.getPlayer().updateInventory();
                    }
                }
            }
        }

    }

    @Override
    public void run() {
        Collection onlineplayer = Bukkit.getOnlinePlayers();
        Iterator<Player> itstring = onlineplayer.iterator();
        Player value;
        while(itstring.hasNext()){
            value = itstring.next();
            if(value != null){
                String name = value.getName();
                if(Main.getInstance().getCenderPearl().getInt(name) > 0){
                    int a = Main.getInstance().getCenderPearl().getInt(name);
                    a--;
                    Main.getInstance().getCenderPearl().set(name,a);
                    try {
                        Main.getInstance().getCenderPearl().save(Main.getInstance().Cenderpearl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
