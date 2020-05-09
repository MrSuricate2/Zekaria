package fr.mrsuricate.zekaria.commands;

import fr.mrsuricate.zekaria.Main;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import java.util.Arrays;

public class spawner implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("spawners")) {
                Inventory inv = Bukkit.createInventory(null, 9, "§6Spawners");

                //Squelleton
                ItemStack squelette = new ItemStack(Material.IRON_SWORD, 1);
                ItemMeta squeletteM = squelette.getItemMeta();
                squeletteM.setDisplayName("§6Spawner Squelette");
                squeletteM.setLore(Arrays.asList(new String[] { "§4Prix: 6 500 $" }));
                squelette.setItemMeta(squeletteM);
                inv.setItem(0, squelette);

                //Zombie
                ItemStack zombie = new ItemStack(Material.GOLD_SWORD, 1);
                ItemMeta zombieM = zombie.getItemMeta();
                zombieM.setDisplayName("§6Spawner à Zombie");
                zombieM.setLore(Arrays.asList(new String[] { "§4Pirx: 6 500 $" }));
                zombie.setItemMeta(zombieM);
                inv.setItem(1, zombie);

                //Vache
                ItemStack vache = new ItemStack(Material.COOKED_BEEF, 1);
                ItemMeta vacheM = vache.getItemMeta();
                vacheM.setDisplayName("§6Spawner à Vache");
                vacheM.setLore(Arrays.asList(new String[] { "§4Prix: 4 000 $" }));
                vache.setItemMeta(vacheM);
                inv.setItem(2, vache);

                //Cochon
                ItemStack cochon = new ItemStack(Material.GRILLED_PORK, 1);
                ItemMeta cochonM = cochon.getItemMeta();
                cochonM.setDisplayName("§6Spawner à Cochon");
                cochonM.setLore(Arrays.asList(new String[] { "§4Prix: 4 000 $" }));
                cochon.setItemMeta(cochonM);
                inv.setItem(3, cochon);

                //Poulet
                ItemStack poulet = new ItemStack(Material.COOKED_CHICKEN, 1);
                ItemMeta pouletM = poulet.getItemMeta();
                pouletM.setDisplayName("§6Spawner à Poulet");
                pouletM.setLore(Arrays.asList(new String[] { "§4Prix: 4 000 $" }));
                poulet.setItemMeta(pouletM);
                inv.setItem(4, poulet);

                //Vitre
                ItemStack bars = new ItemStack(Material.STAINED_GLASS_PANE, 1);
                ItemMeta barsM = bars.getItemMeta();
                barsM.setDisplayName(" ");
                bars.setItemMeta(barsM);
                inv.setItem(5, bars);
                inv.setItem(6, bars);
                inv.setItem(7, bars);
                inv.setItem(8, bars);

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
            if (current == null){
                return;
            }

            if (inv.getName().equalsIgnoreCase("§6Spawners")) {
                e.setCancelled(true);

                switch (current.getType()) {
                    //Squelleton
                    case IRON_SWORD:
                        if (balance >= 6500.0D) {
                            ItemStack spawners = new ItemStack(Material.MOB_SPAWNER , 1);
                            ItemMeta meta = spawners.getItemMeta();
                            meta.setDisplayName("§fSpawner à §eSquelette");
                            meta.setLore(null);
                            spawners.setItemMeta(meta);
                            p.getInventory().addItem(spawners);
                            economy.withdrawPlayer(p, 6500.0D);
                            p.closeInventory();
                            p.sendMessage("§8[§4Zekaria§8] §6Vous venez de recevoir votre spawner");
                            break;
                        }
                        p.sendMessage("§8[§4Zekaria§8] §6Vous n'avez pas assez d'argent !");
                        p.closeInventory();
                        break;
                    //Zombie
                    case GOLD_SWORD:
                        if (balance >= 6500.0D) {
                            ItemStack spawners = new ItemStack(Material.MOB_SPAWNER , 1);
                            ItemMeta meta = spawners.getItemMeta();
                            meta.setDisplayName("§fSpawner à §eZombie");
                            meta.setLore(null);
                            spawners.setItemMeta(meta);
                            p.getInventory().addItem(spawners);
                            economy.withdrawPlayer(p, 6500.0D);
                            p.closeInventory();
                            p.sendMessage("§8[§4Zekaria§8] §6Vous venez de recevoir votre spawner");
                            break;
                        }
                        p.sendMessage("§8[§4Zekaria§8] §6Vous n'avez pas assez d'argent !");
                        p.closeInventory();
                        break;
                    //Vache
                    case COOKED_BEEF:
                        if (balance >= 4000.0D) {
                            ItemStack spawners = new ItemStack(Material.MOB_SPAWNER , 1);
                            ItemMeta meta = spawners.getItemMeta();
                            meta.setDisplayName("§fSpawner à §eVache");
                            meta.setLore(null);
                            spawners.setItemMeta(meta);
                            p.getInventory().addItem(spawners);
                            economy.withdrawPlayer(p, 4000.0D);
                            p.closeInventory();
                            p.sendMessage("§8[§4Zekaria§8] §6Vous venez de recevoir votre spawner");
                            break;
                        }
                        p.sendMessage("§8[§4Zekaria§8] §6Vous n'avez pas assez d'argent !");
                        p.closeInventory();
                        break;
                    //Cochon
                    case GRILLED_PORK:
                        if (balance >= 4000.0D) {
                            ItemStack spawners = new ItemStack(Material.MOB_SPAWNER , 1);
                            ItemMeta meta = spawners.getItemMeta();
                            meta.setDisplayName("§fSpawner à §eCochon");
                            meta.setLore(null);
                            spawners.setItemMeta(meta);
                            p.getInventory().addItem(spawners);
                            economy.withdrawPlayer(p, 4000.0D);
                            p.closeInventory();
                            p.sendMessage("§8[§4Zekaria§8] §6Vous venez de recevoir votre spawner");
                            break;
                        }
                        p.sendMessage("§8[§4Zekaria§8] §6Vous n'avez pas assez d'argent !");
                        p.closeInventory();
                        break;
                    //Poulet
                    case COOKED_CHICKEN:
                        if (balance >= 4000.0D) {
                            ItemStack spawners = new ItemStack(Material.MOB_SPAWNER , 1);
                            ItemMeta meta = spawners.getItemMeta();
                            meta.setDisplayName("§fSpawner à §ePoulet");
                            meta.setLore(null);
                            spawners.setItemMeta(meta);
                            p.getInventory().addItem(spawners);
                            economy.withdrawPlayer(p, 4000.0D);
                            p.closeInventory();
                            p.sendMessage("§8[§4Zekaria§8] §6Vous venez de recevoir votre spawner");
                            break;
                        }
                        p.sendMessage("§8[§4Zekaria§8] §6Vous n'avez pas assez d'argent !");
                        p.closeInventory();
                        break;
                }
            }
        }


    }

    @EventHandler
    public void onPlaceSpawner (BlockPlaceEvent e){
        Block b = e.getBlock();
        if(b.getType() == Material.MOB_SPAWNER){
            String creaturename;

            CreatureSpawner creatureSpawner = (CreatureSpawner) e.getBlock().getState();
            String nomdelitem = e.getPlayer().getItemInHand().getItemMeta().getDisplayName().substring(14);
            if(nomdelitem.equalsIgnoreCase("Squelette")){
                creaturename = "SKELETON";
                try{
                    creatureSpawner.setCreatureTypeByName(creaturename);
                } catch (Exception exception) {}
                creatureSpawner.update();
            }
            if(nomdelitem.equalsIgnoreCase("Zombie")){
                creaturename = "ZOMBIE";
                try{
                    creatureSpawner.setCreatureTypeByName(creaturename);
                } catch (Exception exception) {}
                creatureSpawner.update();
            }
            if(nomdelitem.equalsIgnoreCase("Vache")){
                creaturename = "COW";
                try{
                    creatureSpawner.setCreatureTypeByName(creaturename);
                } catch (Exception exception) {}
                creatureSpawner.update();
            }
            if(nomdelitem.equalsIgnoreCase("Cochon")){
                creaturename = "PIG";
                try{
                    creatureSpawner.setCreatureTypeByName(creaturename);
                } catch (Exception exception) {}
                creatureSpawner.update();
            }
            if(nomdelitem.equalsIgnoreCase("Poulet")){
                creaturename = "CHICKEN";
                try{
                    creatureSpawner.setCreatureTypeByName(creaturename);
                } catch (Exception exception) {}
                creatureSpawner.update();
            }


        }
    }


}
