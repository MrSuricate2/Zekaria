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
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Arrays;

public class grades implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("grades") || cmd.getName().equalsIgnoreCase("grade")) {
                Inventory inv = Bukkit.createInventory(null, 9, "§6Grades");

                //Gladiateur
                ItemStack gladiateur = new ItemStack(Material.IRON_HELMET, 1);
                ItemMeta gladiateurM = gladiateur.getItemMeta();
                gladiateurM.setDisplayName("§6Grade Gladiateur");
                gladiateurM.setLore(Arrays.asList(new String[]{"§4Prix: 75 000 $"}));
                gladiateur.setItemMeta(gladiateurM);
                inv.setItem(3, gladiateur);

                //Duc
                ItemStack duc = new ItemStack(Material.GOLD_HELMET, 1);
                ItemMeta ducM = duc.getItemMeta();
                ducM.setDisplayName("§6Grade Duc");
                ducM.setLore(Arrays.asList(new String[]{"§4Prix: 350 000 $"}));
                duc.setItemMeta(ducM);
                inv.setItem(4, duc);

                //Seigneur
                ItemStack seigneur = new ItemStack(Material.DIAMOND_HELMET, 1);
                ItemMeta seigneurM = seigneur.getItemMeta();
                seigneurM.setDisplayName("§6Grade Seigneur");
                seigneurM.setLore(Arrays.asList(new String[]{"§4Prix: 2 000 000 $"}));
                seigneur.setItemMeta(seigneurM);
                inv.setItem(5, seigneur);

                //Vitre
                ItemStack vitre = new ItemStack(Material.STAINED_GLASS_PANE, 1);
                ItemMeta vitreM = vitre.getItemMeta();
                vitreM.setDisplayName(" ");
                vitre.setItemMeta(vitreM);
                inv.setItem(0, vitre);
                inv.setItem(1, vitre);
                inv.setItem(2, vitre);
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
            ItemStack item = e.getCurrentItem();
            Player p = (Player) e.getWhoClicked();
            ItemStack current = e.getCurrentItem();
            double balance = economy.getBalance(p);
            if (current == null) {
                return;
            }
            if (inv.getName().equalsIgnoreCase("§6Grades")) {
                e.setCancelled(true);
                if (item.getItemMeta().getDisplayName().equals(" ")){
                    return;
                }
                switch (current.getType()) {
                    //Gladiateur
                    case IRON_HELMET:
                        if (p.hasPermission("grades.gladiateur")){
                        if (balance >= 75000.0D) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuadd " + p.getName() + " Gladiateur");
                            economy.withdrawPlayer(p, 75000.0D);
                            p.closeInventory();
                            p.sendMessage("§8[§4Zekaria§8] §6Vous êtes maintenant Gladiateur");
                            break;
                        } else {
                            p.sendMessage("§8[§4Zekaria§8] §6Vous n'avez pas assez d'argent !");
                        }
                    } else {
                            p.sendMessage("§8[§4Zekaria§8] §6Vous ne pouvez pas achetez ce grades");
                        }
                        p.closeInventory();
                        break;
                    //Duc
                    case GOLD_HELMET:
                        if (p.hasPermission("grades.duc")) {
                            if (balance >= 350000.0D) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuadd " + p.getName() + " Duc");
                                economy.withdrawPlayer(p, 350000.0D);
                                p.closeInventory();
                                p.sendMessage("§8[§4Zekaria§8] §6Vous êtes maintenant Duc");
                                break;
                            } else {
                                p.sendMessage("§8[§4Zekaria§8] §6Vous n'avez pas assez d'argent !");
                            }
                        } else {
                            p.sendMessage("§8[§4Zekaria§8] §6Vous ne pouvez pas achetez ce grades");
                        }
                        p.closeInventory();
                        break;
                    //Seigneur
                    case DIAMOND_HELMET:
                        if (p.hasPermission("grades.seigneur")) {
                            if (balance >= 2000000.0D) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuadd " + p.getName() + " Seigneur");
                                economy.withdrawPlayer(p, 2000000.0D);
                                p.closeInventory();
                                p.sendMessage("§8[§4Zekaria§8] §6Vous êtes maintenant Seigneur");
                                break;
                            } else {
                                p.sendMessage("§8[§4Zekaria§8] §6Vous n'avez pas assez d'argent !");
                            }
                        } else {
                            p.sendMessage("§8[§4Zekaria§8] §6Vous ne pouvez pas achetez ce grades");
                        }
                        p.closeInventory();
                        break;
                }
            }

        }
    }
}
