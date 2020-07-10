package fr.mrsuricate.zekaria.CustomEnchant;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
    private static boolean hasantivenin = false;

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
            if(Math.random() < 0.03D){
                if(e.getItem().getType() == Material.BOOK || e.getItem().getType() == Material.DIAMOND_SWORD || e.getItem().getType() == Material.GOLD_SWORD || e.getItem().getType() == Material.STONE_SWORD || e.getItem().getType() == Material.IRON_SWORD || e.getItem().getType() == Material.WOOD_SWORD){
                    String mending = "§2Venin";
                    ItemStack item = e.getItem();
                    ItemMeta meta = item.getItemMeta();
                    meta.setLore(Collections.singletonList(mending));
                    item.setItemMeta(meta);
                }
            }
            if(Math.random() < 0.05D){
                if(e.getItem().getType() != Material.DIAMOND_SWORD && e.getItem().getType() != Material.GOLD_SWORD && e.getItem().getType() != Material.STONE_SWORD && e.getItem().getType() != Material.IRON_SWORD && e.getItem().getType() != Material.WOOD_SWORD){
                    if(e.getItem().getType() != Material.DIAMOND_PICKAXE && e.getItem().getType() != Material.GOLD_PICKAXE && e.getItem().getType() != Material.STONE_PICKAXE && e.getItem().getType() != Material.IRON_PICKAXE && e.getItem().getType() != Material.WOOD_PICKAXE){
                        if(e.getItem().getType() != Material.DIAMOND_AXE && e.getItem().getType() != Material.GOLD_AXE && e.getItem().getType() != Material.STONE_AXE && e.getItem().getType() != Material.IRON_AXE && e.getItem().getType() != Material.WOOD_AXE){
                            if(e.getItem().getType() != Material.DIAMOND_SPADE && e.getItem().getType() != Material.GOLD_SPADE && e.getItem().getType() != Material.STONE_SPADE && e.getItem().getType() != Material.IRON_SPADE && e.getItem().getType() != Material.WOOD_SPADE){
                                if(e.getItem().getType() != Material.DIAMOND_HOE && e.getItem().getType() != Material.GOLD_HOE && e.getItem().getType() != Material.STONE_HOE && e.getItem().getType() != Material.IRON_HOE && e.getItem().getType() != Material.WOOD_HOE){
                                    String mending = "§2Anti-Venin";
                                    ItemStack item = e.getItem();
                                    ItemMeta meta = item.getItemMeta();
                                    meta.setLore(Collections.singletonList(mending));
                                    item.setItemMeta(meta);
                                }
                            }
                        }
                    }
                }
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
                        if(inv.getItem(0).getType().equals(Material.ENCHANTED_BOOK) || inv.getItem(0).getType().equals(Material.LEATHER_HELMET) || inv.getItem(0).getType().equals(Material.IRON_HELMET) || inv.getItem(0).getType().equals(Material.GOLD_HELMET) || inv.getItem(0).getType().equals(Material.CHAINMAIL_HELMET) || inv.getItem(0).getType().equals(Material.DIAMOND_HELMET)){
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
                    if(inv.getItem(0) != null){
                        if(inv.getItem(0).getType().equals(Material.ENCHANTED_BOOK) || inv.getItem(0).getType().equals(Material.LEATHER_HELMET) || inv.getItem(0).getType().equals(Material.IRON_HELMET) || inv.getItem(0).getType().equals(Material.GOLD_HELMET) || inv.getItem(0).getType().equals(Material.CHAINMAIL_HELMET) || inv.getItem(0).getType().equals(Material.DIAMOND_HELMET) || inv.getItem(0).getType().equals(Material.LEATHER_CHESTPLATE) || inv.getItem(0).getType().equals(Material.IRON_CHESTPLATE) || inv.getItem(0).getType().equals(Material.GOLD_CHESTPLATE) || inv.getItem(0).getType().equals(Material.CHAINMAIL_CHESTPLATE) || inv.getItem(0).getType().equals(Material.DIAMOND_CHESTPLATE) || inv.getItem(0).getType().equals(Material.LEATHER_LEGGINGS) || inv.getItem(0).getType().equals(Material.IRON_LEGGINGS) || inv.getItem(0).getType().equals(Material.GOLD_LEGGINGS) || inv.getItem(0).getType().equals(Material.CHAINMAIL_LEGGINGS) || inv.getItem(0).getType().equals(Material.DIAMOND_LEGGINGS) || inv.getItem(0).getType().equals(Material.LEATHER_BOOTS) || inv.getItem(0).getType().equals(Material.IRON_BOOTS) || inv.getItem(0).getType().equals(Material.GOLD_BOOTS) || inv.getItem(0).getType().equals(Material.CHAINMAIL_BOOTS) || inv.getItem(0).getType().equals(Material.DIAMOND_BOOTS)){
                            if (meta.getLore() != null){
                                if(meta.getLore().contains("§2Anti-Venin")){
                                    if(inv.getItem(2) != null){
                                        String mending = "§2Anti-Venin";
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
                    if(inv.getItem(0) != null){
                        if(inv.getItem(0).getType().equals(Material.ENCHANTED_BOOK) || inv.getItem(0).getType().equals(Material.DIAMOND_SWORD) || inv.getItem(0).getType().equals(Material.IRON_SWORD) || inv.getItem(0).getType().equals(Material.GOLD_SWORD) || inv.getItem(0).getType().equals(Material.STONE_SWORD) || inv.getItem(0).getType().equals(Material.WOOD_SWORD)){
                            if (meta.getLore() != null){
                                if(meta.getLore().contains("§2Venin")){
                                    if(inv.getItem(2) != null){
                                        String mending = "§2Venin";
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
                        player.sendMessage("Mending, incassable, night_vision, Venin, Anti-Venin");
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
                        if(args[1].equalsIgnoreCase("Venin")){
                            String mending = "§2Venin";
                            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK,1);
                            ItemMeta meta = item.getItemMeta();
                            meta.setLore(Collections.singletonList(mending));
                            item.setItemMeta(meta);
                            ((Player) sender).getInventory().addItem(item);
                        }
                        if(args[1].equalsIgnoreCase("Anti-Venin")){
                            String mending = "§2Anti-Venin";
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
                            if(args[1].equalsIgnoreCase("Venin")){
                                String mending = "§2Venin";
                                ItemMeta meta = item.getItemMeta();
                                if(meta.hasLore()){
                                    List<String> lore = meta.getLore();
                                    if(!lore.contains("§2Venin")){
                                        lore.add(mending);
                                        meta.setLore(lore);
                                        item.setItemMeta(meta);
                                    } else {
                                        sender.sendMessage("Cette item contient déja le Venin");
                                    }
                                } else {
                                    meta.setLore(Collections.singletonList(mending));
                                    item.setItemMeta(meta);
                                }
                            }
                            if(args[1].equalsIgnoreCase("Anti-Venin")){
                                String mending = "§2Anti-Venin";
                                ItemMeta meta = item.getItemMeta();
                                if(meta.hasLore()){
                                    List<String> lore = meta.getLore();
                                    if(!lore.contains("§2Anti-Venin")){
                                        lore.add(mending);
                                        meta.setLore(lore);
                                        item.setItemMeta(meta);
                                    } else {
                                        sender.sendMessage("Cette item contient déja l'Anti-Venin");
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

    @EventHandler
    public void onEntreringCombats(EntityDamageByEntityEvent e){
        boolean isCitizensNPC = e.getEntity().hasMetadata("NPC");
        if (e.getEntity() instanceof Player){
            if(e.getDamager() instanceof  Player) {
                if(!isCitizensNPC){
                    Player Attaquant = ((Player) e.getDamager()).getPlayer();
                    ItemStack item = Attaquant.getItemInHand();
                    if(item.getType() != Material.AIR){
                        ItemMeta meta = item.getItemMeta();
                        if(meta.hasLore()){
                            List<String> lore = meta.getLore();
                            if(lore.contains("§2Venin")){
                                Player Defenseur = ((Player) e.getEntity()).getPlayer();
                                ItemStack Casque = Defenseur.getInventory().getHelmet();
                                if(Casque != null){
                                    ItemMeta metacasque = Casque.getItemMeta();
                                    if(metacasque.hasLore()){
                                        if(metacasque.getLore().contains("§2Anti-Venin")){
                                            this.hasantivenin = true;
                                        }
                                    }
                                }
                                ItemStack Plastron = Defenseur.getInventory().getChestplate();
                                if(Plastron != null){
                                    ItemMeta metaplastron = Plastron.getItemMeta();
                                    if(metaplastron.hasLore()){
                                        if(metaplastron.getLore().contains("§2Anti-Venin")){
                                            this.hasantivenin = true;
                                        }
                                    }
                                }
                                ItemStack Pantalon = Defenseur.getInventory().getLeggings();
                                if(Pantalon != null){
                                    ItemMeta metapantalon = Pantalon.getItemMeta();
                                    if(metapantalon.hasLore()){
                                        if(metapantalon.getLore().contains("§2Anti-Venin")){
                                            this.hasantivenin = true;
                                        }
                                    }
                                }
                                ItemStack Botte = Defenseur.getInventory().getBoots();
                                if(Botte != null){
                                    ItemMeta metabotte = Botte.getItemMeta();
                                    if(metabotte.hasLore()){
                                        if(metabotte.getLore().contains("§2Anti-Venin")){
                                            this.hasantivenin = true;
                                        }
                                    }
                                }
                                MPlayer Causedamage = MPlayer.get(Attaquant);
                                MPlayer takedamage = MPlayer.get(((Player) e.getEntity()).getPlayer());
                                Faction CauseDamageFaction = Causedamage.getFaction();
                                Faction takedamageFaction = takedamage.getFaction();
                                Rel relation1 = CauseDamageFaction.getRelationWish(takedamageFaction);
                                Rel relation2 = takedamageFaction.getRelationWish(CauseDamageFaction);
                                if(!hasantivenin){
                                    if(CauseDamageFaction != takedamageFaction){
                                        if(!relation1.equals(Rel.ALLY) || !relation2.equals(Rel.ALLY)){
                                            Defenseur.addPotionEffect(new PotionEffect(PotionEffectType.POISON,200, 0,false,false));
                                        }
                                    }
                                }
                                this.hasantivenin = false;
                            }
                        }
                    }
                }
            }
        }
    }
}
