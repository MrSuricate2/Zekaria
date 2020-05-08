package fr.mrsuricate.zekaria.Décocombats;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class DécoCombats extends BukkitRunnable implements Listener {

    private static HashMap<Player, Integer> damagelist = new HashMap<>();
    private short tempo = 10;
    private static Player takedamage;
    private static Player causedamage;

    @EventHandler
    public void onEntreringCombats(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player){
            if(e.getDamager() instanceof  Player){
                this.takedamage = ((Player) e.getEntity()).getPlayer();
                this.causedamage = (Player) e.getDamager();
                damagelist.put(takedamage,1);
                damagelist.put(causedamage,1);
                System.out.println("damage");
                new DécoCombats().runTaskTimer(Main.getInstance(),0L, 20L);
            }
        }
    }

    @EventHandler
    public void OnQuit(PlayerQuitEvent e){
        Player playerquit = e.getPlayer();
        if(this.damagelist.containsKey(playerquit)){
            playerquit.setHealth(0);
            Bukkit.broadcastMessage("§cLe déco-combats est interdit");
            damagelist.remove(takedamage);
            damagelist.remove(causedamage);

        }
    }

    @EventHandler
    public void commandDisable(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String msg = e.getMessage();
        if (damagelist.containsKey(p)){
            e.setCancelled(true);
            p.sendMessage("§cAucune commande n'est autorisé en combat");
        }
    }


    @Override
    public void run() {
        tempo--;
        if(tempo == 0){
            damagelist.remove(takedamage);
            damagelist.remove(causedamage);
            tempo = 10;
            this.cancel();
        }
    }
}
