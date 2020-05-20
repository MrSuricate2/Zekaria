package fr.mrsuricate.zekaria.CustomEnchant;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class Enchant extends BukkitRunnable implements Listener {

    private static Player value;

    @EventHandler
    public void onEnhantmentEvent(EnchantItemEvent e) {
        if(e.getExpLevelCost() == 30){
            if(Math.random() < 0.02D){
                ItemStack item = e.getItem();
                ItemMeta meta = item.getItemMeta();
                meta.spigot().setUnbreakable(true);
                item.setItemMeta(meta);
            }
            if(Math.random() < 0.04D){
                String mending = "§7Mending I";
                ItemStack item = e.getItem();
                ItemMeta meta = item.getItemMeta();
                meta.setLore(Collections.singletonList(mending));
                item.setItemMeta(meta);
            }
        }/*
        if(e.getExpLevelCost() >= 25){
            if(e.getItem().getType() == Material.DIAMOND_HELMET || e.getItem().getType() == Material.IRON_HELMET || e.getItem().getType() == Material.GOLD_HELMET || e.getItem().getType() == Material.CHAINMAIL_HELMET || e.getItem().getType() == Material.LEATHER_HELMET){
                if(Math.random() < 0.06D){
                    String mending = "§7Vision nocturne I";
                    ItemStack item = e.getItem();
                    ItemMeta meta = item.getItemMeta();
                    meta.setLore(Collections.singletonList(mending));
                    item.setItemMeta(meta);
                }
            }

        }*/
    }

    @EventHandler
    public void onEnclumeEvent(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.ANVIL && e.getWhoClicked() instanceof Player) {
            Player player = (Player)e.getWhoClicked();
            AnvilInventory inv = (AnvilInventory)e.getInventory();
            if(e.getSlotType().equals(InventoryType.SlotType.CRAFTING)){

            }
            ItemStack item2 = inv.getItem(1);
            int i = e.getRawSlot();
            InventoryType.SlotType slot = e.getSlotType();
            ItemStack item = e.getCurrentItem();
            System.out.println(slot);
            System.out.println(item);
            System.out.println(item2);
            System.out.println(i);
        }
    }

    @Override
    public void run() {

        Collection onlineplayer = Bukkit.getOnlinePlayers();
        Iterator<Player> itstring = onlineplayer.iterator();
        while(itstring.hasNext()){
            this.value = itstring.next();
        }
        if(this.value != null){
            if(this.value.getPlayer().getInventory().getHelmet() != null){
                if(this.value.getPlayer().getInventory().getHelmet().getItemMeta().getLore() != null){
                    if (this.value.getPlayer().getInventory().getHelmet().getItemMeta().getLore().contains("§7Vision nocturne I")){
                        value.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,60, 0,false,false));
                    }
                }
            }
        }
    }


    @EventHandler
    public void onGetXP(PlayerExpChangeEvent e){
        Player p = e.getPlayer();
        int montant = e.getAmount()*2;
        if(p.getItemInHand() != null){
            if(p.getItemInHand().getItemMeta() != null){
                if(p.getItemInHand().getItemMeta().getLore() != null){
                    if(p.getItemInHand().getItemMeta().getLore().contains("§7Mending I")){
                        if(p.getItemInHand().getDurability() != 0){
                            p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability() - montant));
                            return;
                        }
                    }
                }
            }
        }
        if(p.getInventory().getHelmet() != null){
            if(p.getInventory().getHelmet().getItemMeta().getLore() != null){
                if(p.getInventory().getHelmet().getItemMeta().getLore().contains("§7Mending I")){
                    if(p.getInventory().getHelmet().getDurability() !=0){
                        p.getInventory().getHelmet().setDurability((short) (p.getInventory().getHelmet().getDurability() - montant));
                        return;
                    }
                }
            }
        }
        if(p.getInventory().getChestplate() != null){
            if(p.getInventory().getChestplate().getItemMeta().getLore() != null){
                if(p.getInventory().getChestplate().getItemMeta().getLore().contains("§7Mending I")){
                    if(p.getInventory().getChestplate().getDurability() !=0){
                        p.getInventory().getChestplate().setDurability((short) (p.getInventory().getChestplate().getDurability() - montant));
                        return;
                    }
                }
            }
        }
        if(p.getInventory().getLeggings() != null){
            if(p.getInventory().getLeggings().getItemMeta().getLore() != null){
                if(p.getInventory().getLeggings().getItemMeta().getLore().contains("§7Mending I")){
                    if(p.getInventory().getLeggings().getDurability() !=0){
                        p.getInventory().getLeggings().setDurability((short) (p.getInventory().getLeggings().getDurability() - montant));
                        return;
                    }
                }
            }
        }
        if(p.getInventory().getBoots() != null){
            if(p.getInventory().getBoots().getItemMeta().getLore() != null){
                if(p.getInventory().getBoots().getItemMeta().getLore().contains("§7Mending I")){
                    if(p.getInventory().getBoots().getDurability() !=0){
                        p.getInventory().getBoots().setDurability((short) (p.getInventory().getBoots().getDurability() - montant));
                        return;
                    }
                }
            }
        }
    }
}
