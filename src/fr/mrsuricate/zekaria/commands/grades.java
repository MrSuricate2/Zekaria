package fr.mrsuricate.zekaria.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class grades implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("grades")) {
                Inventory inv = Bukkit.createInventory(null, 9, "§6Grades");
                ItemStack gladiateur = new ItemStack(Material.IRON_HELMET, 1);
                ItemMeta gladiateurM = gladiateur.getItemMeta();
                gladiateurM.setDisplayName("§6Grade Gladiateur");
                gladiateurM.setLore(Arrays.asList(new String[]{"§4Prix: 75 000 $"}));
                gladiateur.setItemMeta(gladiateurM);
                inv.setItem(3, gladiateur);
                ItemStack duc = new ItemStack(Material.GOLD_HELMET, 1);
                ItemMeta ducM = duc.getItemMeta();
                ducM.setDisplayName("§6Grade Duc");
                ducM.setLore(Arrays.asList(new String[]{"§4Prix: 350 000 $"}));
                duc.setItemMeta(ducM);
                inv.setItem(4, duc);
                ItemStack seigneur = new ItemStack(Material.DIAMOND_HELMET, 1);
                ItemMeta seigneurM = seigneur.getItemMeta();
                seigneurM.setDisplayName("§6Grade Seigneur");
                seigneurM.setLore(Arrays.asList(new String[]{"§4Prix: 2 000 000 $"}));
                seigneur.setItemMeta(seigneurM);
                inv.setItem(5, seigneur);
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
}
