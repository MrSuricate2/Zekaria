package fr.mrsuricate.zekaria.enchere;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Lancementenchere extends BukkitRunnable {

    public int tempo = 600;
    public Player player;
    public ItemStack item;

    public Lancementenchere(Player player, ItemStack item){
        this.item = item;
        this.player = player;
    }

    @Override
    public void run() {
        tempo--;

        if(tempo == 0){
            Main.getInstance().enchereEnCours = 0;
            Main.getInstance().data.remove(player);
            this.cancel();
            return;
        }

        player.sendMessage("ceci est une tempo : il reste " + tempo);

    }
}
