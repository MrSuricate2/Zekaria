package fr.mrsuricate.zekaria.commands;

import fr.mrsuricate.zekaria.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Arrays;

public class buykit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("buykits")) {
                Inventory inv = Bukkit.createInventory(null, 9, "§6Achat de kit");
                ItemStack pillage = new ItemStack(Material.TNT);
                ItemMeta pillageM = pillage.getItemMeta();
                pillageM.setDisplayName("§6Kit Pillage");
                pillageM.setLore(Arrays.asList(new String[]{"§4Prix: 200 000 $"}));
                pillage.setItemMeta(pillageM);
                inv.setItem(0, pillage);
                ItemStack potion = new ItemStack(Material.POTION, 1);
                ItemMeta potionM = potion.getItemMeta();
                potionM.setDisplayName("§6Kit Potion");
                potionM.setLore(Arrays.asList(new String[]{"§4Prix: 200 000 $"}));
                potion.setItemMeta(potionM);
                inv.setItem(1, potion);
                ItemStack constru = new ItemStack(Material.OBSIDIAN, 1);
                ItemMeta construM = constru.getItemMeta();
                construM.setDisplayName("§6Kit Construction");
                construM.setLore(Arrays.asList(new String[]{"§4Prix: 200 000 $"}));
                constru.setItemMeta(construM);
                inv.setItem(2, constru);
                ItemStack enchant = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta enchantM = enchant.getItemMeta();
                enchantM.setDisplayName("§6Kit Enchanteur");
                enchantM.setLore(Arrays.asList(new String[]{"§4Prix: 200 000 $"}));
                enchant.setItemMeta(enchantM);
                inv.setItem(3, enchant);
                ItemStack vitre = new ItemStack(Material.STAINED_GLASS_PANE, 1);
                ItemMeta vitreM = vitre.getItemMeta();
                vitreM.setDisplayName(" ");
                vitre.setItemMeta(vitreM);
                inv.setItem(4, vitre);
                inv.setItem(5, vitre);
                inv.setItem(6, vitre);
                inv.setItem(7, vitre);
                inv.setItem(8, vitre);
                player.openInventory(inv);
                return true;
            }
        }
        return false;
    }

    public static Economy economy = null;

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Main.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null)
            economy = economyProvider.getProvider();
        return (economy != null);
    }

    @EventHandler
    public void ClickEvent(InventoryClickEvent e) {
        if (setupEconomy()) {
            Inventory inv = e.getInventory();
            Player p = (Player) e.getWhoClicked();
            ItemStack current = e.getCurrentItem();
            double balance = economy.getBalance(p);
            if (current == null)
                return;
            if (inv.getName().equalsIgnoreCase("")) {
                e.setCancelled(true);
                switch (current.getType()) {
                    case IRON_SWORD:
                        if (balance >= 6500.0D) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + p.getName() + " Skeleton 1");
                            economy.withdrawPlayer(p, 6500.0D);
                            p.closeInventory();
                            p.sendMessage("venez de recevoir votre spawner");
                            break;
                        }
                        p.sendMessage("n'avez pas assez d'argent !");
                        p.closeInventory();
                        break;
                    case GOLD_SWORD:
                        if (balance >= 6500.0D) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + p.getName() + " Zombie 1");
                            economy.withdrawPlayer(p, 6500.0D);
                            p.closeInventory();
                            p.sendMessage("venez de recevoir votre spawner");
                            break;
                        }
                        p.sendMessage("n'avez pas assez d'argent !");
                        p.closeInventory();
                        break;
                    case DIAMOND_SWORD:
                        if (balance >= 80000.0D) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + p.getName() + " VillagerGolem 1");
                            economy.withdrawPlayer(p, 80000.0D);
                            p.closeInventory();
                            p.sendMessage("venez de recevoir votre spawner");
                            break;
                        }
                        p.sendMessage("n'avez pas assez d'argent !");
                        p.closeInventory();
                        break;
                    case COOKED_BEEF:
                        if (balance >= 4000.0D) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + p.getName() + " Cow 1");
                            economy.withdrawPlayer(p, 4000.0D);
                            p.closeInventory();
                            p.sendMessage("venez de recevoir votre spawner");
                            break;
                        }
                        p.sendMessage("n'avez pas assez d'argent !");
                        p.closeInventory();
                        break;
                    case GRILLED_PORK:
                        if (balance >= 4000.0D) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + p.getName() + " Pig 1");
                            economy.withdrawPlayer(p, 4000.0D);
                            p.closeInventory();
                            p.sendMessage("venez de recevoir votre spawner");
                            break;
                        }
                        p.sendMessage("n'avez pas assez d'argent !");
                        p.closeInventory();
                        break;
                    case COOKED_CHICKEN:
                        if (balance >= 4000.0D) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + p.getName() + " Chicken 1");
                            economy.withdrawPlayer(p, 4000.0D);
                            p.closeInventory();
                            p.sendMessage("venez de recevoir votre spawner");
                            break;
                        }
                        p.sendMessage("n'avez pas assez d'argent !");
                        p.closeInventory();
                        break;
                }
            }
        }


    }
}
