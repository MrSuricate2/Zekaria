package fr.mrsuricate.zekaria.CoinFlip.events;

import fr.mrsuricate.zekaria.CoinFlip.utilz.Chat;
import fr.mrsuricate.zekaria.CoinFlip.utilz.CoinManager;
import fr.mrsuricate.zekaria.CoinFlip.utilz.InventoryManager;
import fr.mrsuricate.zekaria.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {
    @EventHandler
    public void onClickEvent(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        Inventory open = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();
        Economy econ = Main.getEconomy();
        CoinManager coins = Main.getInstance().getCoins();
        InventoryManager menu = Main.getInstance().getMenuManager();
        if (item == null || !item.hasItemMeta())
            return;
        if (open.getName().equals(Main.getInstance().getMenu().getName()) || open.getTitle().equals(Chat.color(Main.getInstance().getConfig().getString("AnimationGUI.Title")))) {
            e.setCancelled(true);
            if (item.equals(menu.playerRefresh())) {
                p.closeInventory();
                p.openInventory(menu.getMenu());
                return;
            }
            Player other = Bukkit.getServer().getPlayer(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
            if (item.equals(menu.playerHelp()))
                return;
            if (coins.getEntry().containsKey(p))
                return;
            if (item.getItemMeta().getDisplayName().equals(Chat.color(Main.getInstance().getConfig().getString("GUI.Auto-Refill.display_name"))))
                return;
            if (item.getItemMeta().getDisplayName().equals(Chat.color("&0")))
                return;
            if (item.getType().equals(Material.SKULL_ITEM)) {
                if (coins.inEntry(other)) {
                    double amount = coins.getEntry().get(other).getAmount();
                    if (econ.getBalance(p) >= amount) {
                        econ.withdrawPlayer(p, amount);
                        p.closeInventory();
                        coins.removeEntry(other);
                        Player winner = Main.getInstance().getStats().getWinner(p, other);
                        amount *= 2.0D;
                        if (p.equals(winner)) {
                            Main.getInstance().getAnimation().setAnimation(p, p, other, amount);
                        } else {
                            Main.getInstance().getAnimation().setAnimation(p, other, p, amount);
                        }
                        menu.updateInv();
                    } else {
                        p.closeInventory();
                        p.sendMessage(Chat.color(Main.getInstance().getConfig().getString("Messages.NotEnoughMoney")));
                    }
                }
            } else if (item.getItemMeta().getDisplayName().equals(Chat.color("&f&l" + other.getName()))) {
                return;
            }
        }
    }
}
