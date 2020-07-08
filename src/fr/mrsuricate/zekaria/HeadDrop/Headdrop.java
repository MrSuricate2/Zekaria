package fr.mrsuricate.zekaria.HeadDrop;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import skinsrestorer.bukkit.SkinsRestorer;
import skinsrestorer.bukkit.SkinsRestorerBukkitAPI;

public class Headdrop implements Listener {

    private FileConfiguration config = Main.getInstance().getConfig();

    @EventHandler
    public void death(PlayerDeathEvent e) {

        if (e.getEntity().getKiller() instanceof Player) {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(e.getEntity().getPlayer().getName());
            skull.setItemMeta(meta);
            if(Math.random() < 0.25D){
                e.getEntity().getWorld().dropItem(e.getEntity().getPlayer().getLocation(), skull);
            }
        }
    }

}
