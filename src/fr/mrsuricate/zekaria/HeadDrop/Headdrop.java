package fr.mrsuricate.zekaria.HeadDrop;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import skinsrestorer.bukkit.SkinsRestorer;

public class Headdrop implements Listener {

    private FileConfiguration config = Main.getInstance().getConfig();

    @EventHandler
    public void death(PlayerDeathEvent e) {

        if (e.getEntity().getKiller() instanceof Player) {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            if (this.config.getString("SkinRestorer-enable").equalsIgnoreCase("true")) {
                String name = e.getEntity().getPlayer().getName();
                String skin = String.valueOf(SkinsRestorer.getInstance().getSkinsRestorerBukkitAPI().getSkinName(name));
                if (skin.equalsIgnoreCase("null")) {
                    meta.setOwner(e.getEntity().getPlayer().getName());
                    skull.setItemMeta(meta);
                } else {
                    meta.setOwner(skin);
                    skull.setItemMeta(meta);
                }
            } else {
                meta.setOwner(e.getEntity().getPlayer().getName());
                skull.setItemMeta(meta);
            }
            e.getEntity().getWorld().dropItem(e.getEntity().getPlayer().getLocation(), skull);
        }
    }

}
