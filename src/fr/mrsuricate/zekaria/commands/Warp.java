package fr.mrsuricate.zekaria.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Warp implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("warp")){
                Inventory inv = Bukkit.createInventory(null, 9, "§6Warp");

                //Warp cle
                ItemStack cle = new ItemStack(Material.TRIPWIRE_HOOK, 1);
                ItemMeta cleM = cle.getItemMeta();
                cleM.setDisplayName("§bWarp Cle");
                cleM.setLore(Arrays.asList("§9Clique ici pour te téléporter"));
                cle.setItemMeta(cleM);
                inv.setItem(0, cle);

                //Warp forge
                ItemStack forge = new ItemStack(Material.ANVIL, 1);
                ItemMeta forgeM = forge.getItemMeta();
                forgeM.setDisplayName("§bWarp Forge");
                forgeM.setLore(Arrays.asList("§9Clique ici pour te téléporter"));
                forge.setItemMeta(forgeM);
                inv.setItem(1, forge);

                //Warp Jump 1
                ItemStack jumpa = new ItemStack(Material.FEATHER, 1);
                ItemMeta jumpaM = jumpa.getItemMeta();
                jumpaM.setDisplayName("§bWarp Jump 1");
                jumpaM.setLore(Arrays.asList("§9Clique ici pour te téléporter"));
                jumpa.setItemMeta(jumpaM);
                inv.setItem(2, jumpa);

                //Warp Jump 2
                ItemStack jumpb = new ItemStack(Material.STRING, 1);
                ItemMeta jumpbM = jumpb.getItemMeta();
                jumpbM.setDisplayName("§bWarp Jump 2");
                jumpbM.setLore(Arrays.asList("§9Clique ici pour te téléporter"));
                jumpb.setItemMeta(jumpbM);
                inv.setItem(3, jumpb);

                //Warp Mine
                ItemStack mine = new ItemStack(Material.DIAMOND_PICKAXE, 1);
                ItemMeta mineM = mine.getItemMeta();
                mineM.setDisplayName("§bWarp Mine");
                mineM.setLore(Arrays.asList("§9Clique ici pour te téléporter"));
                mine.setItemMeta(mineM);
                inv.setItem(4, mine);

                p.openInventory(inv);

                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void ClickEvent(InventoryClickEvent e){
        Inventory inv = e.getInventory();
        ItemStack current = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();

        if (current == null){
            return;
        }

        if (inv.getName().equalsIgnoreCase("§6Warp")){
            CommandSender console = Bukkit.getServer().getConsoleSender();
            switch (current.getType()){
                //Warp clef
                case TRIPWIRE_HOOK:
                    e.setCancelled(true);
                    p.sendMessage("§8[§4Zekaria§8] §6Téléportation dans 3 secondes");
                    p.closeInventory();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException a){
                        System.out.println(a);
                    }
                    Bukkit.dispatchCommand(console, "essentials:warp clef " + e.getWhoClicked().getName());
                    break;
                    //warp forge
                case ANVIL:
                    e.setCancelled(true);
                    p.sendMessage("§8[§4Zekaria§8] §6Téléportation dans 3 secondes");
                    p.closeInventory();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException a){
                        System.out.println(a);
                    }
                    Bukkit.dispatchCommand(console, "essentials:warp forge " + e.getWhoClicked().getName());
                    break;
                    //Warp Jump
                case FEATHER:
                    e.setCancelled(true);
                    p.sendMessage("§8[§4Zekaria§8] §6Téléportation dans 3 secondes");
                    p.closeInventory();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException a){
                        System.out.println(a);
                    }
                    Bukkit.dispatchCommand(console, "essentials:warp jump " + e.getWhoClicked().getName());
                    break;
                    //Warp jump 2
                case STRING:
                    e.setCancelled(true);
                    p.sendMessage("§8[§4Zekaria§8] §6Téléportation dans 3 secondes");
                    p.closeInventory();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException a){
                        System.out.println(a);
                    }
                    Bukkit.dispatchCommand(console, "essentials:warp jump2 " + e.getWhoClicked().getName());
                    break;
                    //Warp mine
                case DIAMOND_PICKAXE:
                    e.setCancelled(true);
                    p.sendMessage("§8[§4Zekaria§8] §6Téléportation dans 3 secondes");
                    p.closeInventory();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException a){
                        System.out.println(a);
                    }
                    Bukkit.dispatchCommand(console, "essentials:warp mine " + e.getWhoClicked().getName());
                    break;

                default: break;
            }
        }
    }
}
