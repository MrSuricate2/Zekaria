package fr.mrsuricate.zekaria.events;

import fr.mrsuricate.zekaria.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

public class DeathMoney implements Listener {

    public static Economy economy = null;

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Main.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null)
            economy = economyProvider.getProvider();
        return (economy != null);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if (setupEconomy()) {
            Player p = e.getEntity();
            double balance = economy.getBalance(p);

            if (balance <= 0.0D) {
                p.sendMessage("§8[§4Zekaria§8] §6Vous ne perdez plus d'argent car votre solde est nul");
            } else {
                p.sendMessage("§8[§4Zekaria§8] §6Vous venez de perdre 20 $");
                economy.withdrawPlayer(p, 20.0D);
            }
        }
    }

}
