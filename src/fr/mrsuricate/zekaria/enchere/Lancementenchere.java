package fr.mrsuricate.zekaria.enchere;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Lancementenchere extends BukkitRunnable {

    public int tempo = 60;
    public Player player;
    public ItemStack item;

    public Lancementenchere(Player player, ItemStack item){
        this.item = item;
        this.player = player;
    }

    @Override
    public void run() {
        tempo--;
        int minute = tempo/60;
        int seconde = tempo - minute*60;
        System.out.println(minute + seconde);
        if(tempo == 0){
            if(Main.getInstance().bid.isEmpty()){
                Main.getInstance().namecreate.getInventory().addItem(item);
                Main.getInstance().enchereEnCours = 0;
                this.cancel();
                return;
            } else {
                Player winbid = Main.getInstance().lastbid;
                System.out.println(winbid);

                double retour = Main.getInstance().bid.get(Main.getInstance().lastbid);
                enchere.economy.depositPlayer(player, retour);

                Main.getInstance().bid.remove(Main.getInstance().lastbid);

                winbid.getInventory().addItem(item);


                Main.getInstance().enchereEnCours = 0;
                Main.getInstance().data.remove(player);

                this.cancel();
                return;
            }

        }
    }
}
