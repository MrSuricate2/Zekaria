package fr.mrsuricate.zekaria.CoinFlip.utilz;

import fr.mrsuricate.zekaria.Main;
import java.text.DecimalFormat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AnimationManager {
    private FileConfiguration config = Main.getInstance().getConfig();

    private String title = this.config.getString("AnimationGUI.Title");

    public ItemStack players(Player winner) {
        ItemStack p1 = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta meta = (SkullMeta)Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        meta.setDisplayName(Chat.color("&f&l") + winner.getName());
        p1.setItemMeta((ItemMeta)meta);
        return p1;
    }

    public void fillGlass(int j, Inventory animation) {
        for (int i = 0; i < 27; i++)
            animation.setItem(i, Main.getInstance().getMenuManager().crateGlass(1, j));
    }

    public void setAnimation(final Player p, final Player winner, final Player loser, final double amount) {
        final DecimalFormat df = new DecimalFormat("# ###");
        final Inventory animation = Bukkit.createInventory(null, 27, Chat.color(this.title));
        p.openInventory(animation);
        (new BukkitRunnable() {
            int counter = 0;

            int counter2 = 1;

            int counter3 = 1;

            public void run() {
                AnimationManager.this.fillGlass(this.counter, animation);
                if (this.counter % this.counter2 == 0) {
                    this.counter = 0;
                    this.counter2++;
                }
                if (this.counter3 == 1) {
                    animation.setItem(13, AnimationManager.this.players(loser));
                    p.updateInventory();
                    this.counter3--;
                } else {
                    animation.setItem(13, AnimationManager.this.players(winner));
                    p.updateInventory();
                    this.counter3++;
                }
                this.counter++;
                if (this.counter2 > 6) {
                    AnimationManager.this.fillGlass(15, animation);
                    animation.setItem(13, AnimationManager.this.players(winner));
                    p.updateInventory();
                    Main.getEconomy().depositPlayer((OfflinePlayer)winner, amount);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (!Main.getInstance().getBroadcast().inEntry(p))
                            p.sendMessage(Chat.color(Main.getInstance().getConfig().getString("Messages.WinBroadcast").replaceAll("%winner%", winner.getName())
                                    .replaceAll("%loser%", loser.getName()).replaceAll("%amount%", df.format(amount))));
                    }
                    cancel();
                }
            }
        }).runTaskTimer((Plugin)Main.getInstance(), 0L, 5L);
    }
}
