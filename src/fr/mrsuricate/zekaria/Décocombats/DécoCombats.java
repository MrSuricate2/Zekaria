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
import java.io.IOException;

public class DécoCombats implements Listener {

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
        if (e.getEntity() instanceof Player){
            if(e.getDamager() instanceof  Player) {
                this.takedamage = ((Player) e.getEntity()).getPlayer();
                this.causedamage = (Player) e.getDamager();
                MPlayer mptd = MPlayer.get(this.takedamage);
                MPlayer mpcd = MPlayer.get(this.causedamage);
                if (mptd.getFactionName().equalsIgnoreCase(mpcd.getFactionName())) {
                    if(!mptd.hasFaction() || !mpcd.hasFaction()){
                        Main.getInstance().getDéco_cobatsConfig().set(this.takedamage.getName(),10);
                        Main.getInstance().getDéco_cobatsConfig().set(this.causedamage.getName(),10);
                        try{
                            Main.getInstance().getDéco_cobatsConfig().save(Main.getInstance().Déco_Combats);
                        } catch (IOException er){
                            er.printStackTrace();
                        }
                    } else {
                        return;
                    }
                }
                if (takedamage.isOp() || causedamage.isOp()) {
                    return;
                }
                StateFlag.State container = Main.getInstance().WorldGuard.getRegionManager(takedamage.getWorld()).getApplicableRegions(takedamage.getLocation()).getFlag(DefaultFlag.PVP);
                if (container == null) {
                    Main.getInstance().getDéco_cobatsConfig().set(this.takedamage.getName(),10);
                    Main.getInstance().getDéco_cobatsConfig().set(this.causedamage.getName(),10);
                    try{
                        Main.getInstance().getDéco_cobatsConfig().save(Main.getInstance().Déco_Combats);
                    } catch (IOException er){
                        er.printStackTrace();
                    }
                }
            }
        }
    }

    @EventHandler
    public void OnQuit(PlayerQuitEvent e){
        setupEconomy();
        Player playerquit = e.getPlayer();
        if ((int)Main.getInstance().getDéco_cobatsConfig().get(playerquit.getName()) !=0) {
            playerquit.setHealth(0);
            Double balance = economy.getBalance(playerquit);
            if (balance >= 500D){
                economy.withdrawPlayer(playerquit , 500D);
            }
            Bukkit.broadcastMessage("§cLe déco-combats est interdit");
            Main.getInstance().getDéco_cobatsConfig().set(playerquit.getName(),0);
            try{
                Main.getInstance().getDéco_cobatsConfig().save(Main.getInstance().Déco_Combats);
            } catch (IOException er){
                er.printStackTrace();
            }
        }
    }

    @EventHandler
    public void commandDisable(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        if (Main.getInstance().getDéco_cobatsConfig().contains(p.getName())){
            if((int)Main.getInstance().getDéco_cobatsConfig().get(p.getName()) != 0){
                e.setCancelled(true);
                p.sendMessage("§cAucune commande n'est autorisé en combat");
            }
        }
    }
}
