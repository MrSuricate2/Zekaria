package fr.mrsuricate.zekaria.CustomEnchant;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Enchant extends BukkitRunnable implements Listener, CommandExecutor {

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
        }
        if(e.getExpLevelCost() >= 25){
            if(e.getItem().getType() == Material.DIAMOND_HELMET || e.getItem().getType() == Material.BOOK || e.getItem().getType() == Material.IRON_HELMET || e.getItem().getType() == Material.GOLD_HELMET || e.getItem().getType() == Material.CHAINMAIL_HELMET || e.getItem().getType() == Material.LEATHER_HELMET){
                if(Math.random() < 0.06D){
                    String mending = "§7Vision nocturne I";
                    ItemStack item = e.getItem();
                    ItemMeta meta = item.getItemMeta();
                    meta.setLore(Collections.singletonList(mending));
                    item.setItemMeta(meta);
                }
            }

        }
    }


    //todo changer pour PrepareAnvilEvent
    @EventHandler
    public void onEnclumeEvent(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.ANVIL && e.getWhoClicked() instanceof Player) {
            Player player = (Player)e.getWhoClicked();
            AnvilInventory inv = (AnvilInventory)e.getInventory();
            if(e.getRawSlot() == 2){
                if(inv.getItem(1) != null){
                    ItemMeta meta = inv.getItem(1).getItemMeta();
                    if(meta.spigot().isUnbreakable()){
                        if(inv.getItem(2) != null){
                            ItemStack item = inv.getItem(2);
                            ItemMeta meta2 = item.getItemMeta();
                            meta2.spigot().setUnbreakable(true);
                            item.setItemMeta(meta2);
                            inv.setItem(2,item);
                            return;
                        }
                    }
                    if(meta.getLore() != null) {
                        if(meta.getLore().contains("§7Mending I")){
                            if(inv.getItem(2) != null){
                                String mending = "§7Mending I";
                                List<String> list = inv.getItem(0).getItemMeta().getLore();
                                if(list == null){
                                    ItemStack item = inv.getItem(2);
                                    ItemMeta meta2 = item.getItemMeta();
                                    meta2.setLore(Collections.singletonList(mending));
                                    item.setItemMeta(meta2);
                                    inv.setItem(2,item);
                                    return;
                                }
                                if(!list.contains(mending)){
                                    list.add(mending);
                                    ItemStack item = inv.getItem(2);
                                    ItemMeta meta2 = item.getItemMeta();
                                    meta2.setLore(list);
                                    item.setItemMeta(meta2);
                                    inv.setItem(2,item);
                                    return;
                                }
                            }
                        }
                    }

                    if(inv.getItem(0) != null){
                        if(inv.getItem(0).getType().equals(Material.LEATHER_HELMET) || inv.getItem(0).getType().equals(Material.IRON_HELMET) || inv.getItem(0).getType().equals(Material.GOLD_HELMET) || inv.getItem(0).getType().equals(Material.CHAINMAIL_HELMET) || inv.getItem(0).getType().equals(Material.DIAMOND_HELMET)){
                            if (meta.getLore() != null){
                                if(meta.getLore().contains("§7Vision nocturne I")){
                                    if(inv.getItem(2) != null){
                                        String mending = "§7Vision nocturne I";
                                        List<String> list = inv.getItem(0).getItemMeta().getLore();
                                        if(list == null){
                                            ItemStack item = inv.getItem(2);
                                            ItemMeta meta2 = item.getItemMeta();
                                            meta2.setLore(Collections.singletonList(mending));
                                            item.setItemMeta(meta2);
                                            inv.setItem(2,item);
                                            return;
                                        }
                                        if(!list.contains(mending)){
                                            list.add(mending);
                                            ItemStack item = inv.getItem(2);
                                            ItemMeta meta2 = item.getItemMeta();
                                            meta2.setLore(list);
                                            item.setItemMeta(meta2);
                                            inv.setItem(2,item);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        Collection onlineplayer = Bukkit.getOnlinePlayers();
        Iterator<Player> itstring = onlineplayer.iterator();
        while(itstring.hasNext()){
            this.value = itstring.next();
            if(this.value != null){
                String name = this.value.getName();
                if(this.value.getPlayer().getInventory().getHelmet() != null){
                    if(this.value.getPlayer().getInventory().getHelmet().getItemMeta().getLore() != null){
                        if (this.value.getPlayer().getInventory().getHelmet().getItemMeta().getLore().contains("§7Vision nocturne I")){
                            if(!value.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                                value.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,600, 0,false,false));
                            } else {
                                value.removePotionEffect(PotionEffectType.NIGHT_VISION);
                                if(!value.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                                    value.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,600, 0,false,false));
                                }
                            }
                        }
                    }
                }
                if(Main.getInstance().getpotion().contains(name)){
                    if(Main.getInstance().getpotion().getBoolean(name+".NightVision")){
                        if(!value.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                            value.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,600, 0,false,false));
                        } else {
                            value.removePotionEffect(PotionEffectType.NIGHT_VISION);
                            if(!value.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                                value.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,600, 0,false,false));
                            }
                        }
                    }
                    if(Main.getInstance().getpotion().getBoolean(name+".Hast")){
                        if(!value.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                            value.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,600, 0,false,false));
                        } else {
                            value.removePotionEffect(PotionEffectType.FAST_DIGGING);
                            if(!value.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                                value.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,600, 0,false,false));
                            }
                        }
                    }
                    if(Main.getInstance().getpotion().getBoolean(name+".speed")){
                        if(!value.hasPotionEffect(PotionEffectType.SPEED)){
                            value.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,600, 0,false,false));
                        } else {
                            value.removePotionEffect(PotionEffectType.SPEED);
                            if(!value.hasPotionEffect(PotionEffectType.SPEED)){
                                value.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,600, 0,false,false));
                            }
                        }
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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("CustomEnchant")) {
                if (args.length == 0){
                    player.sendMessage("/CustomEnchant give/apply");
                }
                if (args.length == 1){
                    if(args[0].equalsIgnoreCase("give")|| args[0].equalsIgnoreCase("apply")){
                        player.sendMessage("Mending, incassable, night_vision");
                    }
                }
                if (args.length == 2){
                    if (args[0].equalsIgnoreCase("give")){
                        if(args[1].equalsIgnoreCase("mending")){
                            String mending = "§7Mending I";
                            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK,1);
                            ItemMeta meta = item.getItemMeta();
                            meta.setLore(Collections.singletonList(mending));
                            item.setItemMeta(meta);
                            ((Player) sender).getInventory().addItem(item);
                        }
                        if(args[1].equalsIgnoreCase("incassable")){
                            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK,1);
                            ItemMeta meta = item.getItemMeta();
                            meta.spigot().setUnbreakable(true);
                            item.setItemMeta(meta);
                            ((Player) sender).getInventory().addItem(item);
                        }
                        if(args[1].equalsIgnoreCase("night_vision")){
                            String mending = "§7Vision nocturne I";
                            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK,1);
                            ItemMeta meta = item.getItemMeta();
                            meta.setLore(Collections.singletonList(mending));
                            item.setItemMeta(meta);
                            ((Player) sender).getInventory().addItem(item);
                        }
                    }
                    if (args[0].equalsIgnoreCase("apply")){
                        if(!(((Player) sender).getItemInHand().getType() == Material.AIR)){
                            ItemStack item = ((Player) sender).getItemInHand();
                            if(args[1].equalsIgnoreCase("mending")){
                                String mending = "§7Mending I";
                                ItemMeta meta = item.getItemMeta();
                                if(meta.hasLore()){
                                    List<String> lore = meta.getLore();
                                    if(!lore.contains("§7Mending I")){
                                        lore.add(mending);
                                        meta.setLore(lore);
                                        item.setItemMeta(meta);
                                    } else {
                                        sender.sendMessage("Cette item contient déja mending");
                                    }
                                } else {
                                    meta.setLore(Collections.singletonList(mending));
                                    item.setItemMeta(meta);
                                }
                            }
                            if(args[1].equalsIgnoreCase("incassable")){
                                ItemMeta meta = item.getItemMeta();
                                meta.spigot().setUnbreakable(true);
                                item.setItemMeta(meta);

                            }
                            if(args[1].equalsIgnoreCase("night_vision")){
                                String mending = "§7Vision nocturne I";
                                ItemMeta meta = item.getItemMeta();
                                if(meta.hasLore()){
                                    List<String> lore = meta.getLore();
                                    if(!lore.contains("§7Vision nocturne I")){
                                        lore.add(mending);
                                        meta.setLore(lore);
                                        item.setItemMeta(meta);
                                    } else {
                                        sender.sendMessage("Cette item contient déja night vision");
                                    }
                                } else {
                                    meta.setLore(Collections.singletonList(mending));
                                    item.setItemMeta(meta);
                                }
                            }
                        }

                    }
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        String name = e.getEntity().getName();
        if(Main.getInstance().getpotion().contains(name)){
            Main.getInstance().getpotion().set(name+".NightVision", false);
            Main.getInstance().getpotion().set(name+".Hast", false);
            Main.getInstance().getpotion().set(name+".speed", false);
            try {
                Main.getInstance().getpotion().save(Main.getInstance().potion);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
