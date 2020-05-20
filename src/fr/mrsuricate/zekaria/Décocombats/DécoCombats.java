package fr.mrsuricate.zekaria.Décocombats;

import com.massivecraft.factions.entity.MPlayer;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import fr.mrsuricate.zekaria.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class DécoCombats extends BukkitRunnable implements Listener {

    private static HashMap<Player, Integer> damagelist = new HashMap<>();
    private short tempo = 10;
    private static Player takedamage;
    private static Player causedamage;

    public static Economy economy = null;


    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Main.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null)
            economy = economyProvider.getProvider();
        return (economy != null);
    }

    @EventHandler
    public void onEntreringCombats(EntityDamageByEntityEvent e){
        /*if (e.getEntity() instanceof Player){
            if(e.getDamager() instanceof  Player){
                this.takedamage = ((Player) e.getEntity()).getPlayer();
                this.causedamage = (Player) e.getDamager();
                MPlayer mptd = MPlayer.get(this.takedamage);
                MPlayer mpcd = MPlayer.get(this.causedamage);
                if(mptd.getFactionName().equalsIgnoreCase(mpcd.getFactionName())){
                    return;
                }
                if(takedamage.isOp() || causedamage.isOp()){
                    return;
                } else {
                    StateFlag.State container = Main.getInstance().WorldGuard.getRegionManager(takedamage.getWorld()).getApplicableRegions(takedamage.getLocation()).getFlag(DefaultFlag.PVP);
                    if(container == null){
                        damagelist.put(takedamage,1);
                        damagelist.put(causedamage,1);
                        new DécoCombats().runTaskTimer(Main.getInstance(),0L, 20L);
                    }
                }
            }
        }*/
    }

    @EventHandler
    public void OnQuit(PlayerQuitEvent e){
        setupEconomy();
        Player playerquit = e.getPlayer();
        if (this.damagelist.containsKey(playerquit)) {
            playerquit.setHealth(0);
            Double balance = economy.getBalance(playerquit);
            if (balance >= 500D){
                economy.withdrawPlayer(playerquit , 500D);
            }
            System.out.println(playerquit.getName() + "a ete kill pour deco combat");
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
            System.out.println(damagelist);
            damagelist.remove(takedamage);
            damagelist.remove(causedamage);
            tempo = 10;
            this.cancel();
        }
    }
}
