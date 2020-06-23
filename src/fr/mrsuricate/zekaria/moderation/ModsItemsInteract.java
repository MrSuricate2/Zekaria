package fr.mrsuricate.zekaria.moderation;

import fr.mrsuricate.zekaria.Main;
import fr.mrsuricate.zekaria.moderation.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModsItemsInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        if(Main.getInstance().modlist.containsKey(player.getName())){
            if(!(e.getRightClicked() instanceof  Player)) return;
            Player target = (Player) e.getRightClicked();

            e.setCancelled(true);

            switch(player.getInventory().getItemInHand().getType()){

                // Voir l'inventaire

                case PAPER:
                    Inventory inv = Bukkit.createInventory(null, 5 * 9, target.getName() + " > Inventaire");

                    for(int i = 0; i < 36; i++){
                        if(target.getInventory().getItem(i) != null){
                            inv.setItem(i, target.getInventory().getItem(i));
                        }
                    }

                    inv.setItem(36, target.getInventory().getHelmet());
                    inv.setItem(37, target.getInventory().getChestplate());
                    inv.setItem(38, target.getInventory().getLeggings());
                    inv.setItem(39, target.getInventory().getBoots());

                    player.openInventory(inv);
                    break;

                //Report

                case BOOK:
                    //TODO
                    break;

                //Freeze

                case PACKED_ICE:
                    if (!target.hasPermission("freeze.bypass")) {
                        if (Main.getInstance().freeze.containsKey(target.getName())) {
                            Main.getInstance().freeze.remove(target.getName());
                            target.sendMessage("§6Vous avez été unfreeze par §b" + player.getName());
                            player.sendMessage("§6Vous avez unfreeze §b" + target.getName());
                        } else {
                            Main.getInstance().freeze.put(target.getName(), 1);
                            target.sendMessage("");
                            target.sendMessage("§6Vous avez été freeze par §b" + player.getName());
                            target.sendMessage("§6Merci de venir sur discord : §bhttps://discord.gg/knXYnBG");
                            target.sendMessage("");
                            player.sendMessage("§6Vous avez freeze §b" + target.getName());
                        }
                    } else {
                        player.sendMessage("§6Vous ne pouvez pas freeze §b" + target.getName());
                    }
                    break;

                //Kill player
                case BLAZE_ROD:
                    target.damage(target.getHealth());
                    break;

                default: break;
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(Main.getInstance().modlist.containsKey(player.getName())){
            if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;

            switch(player.getInventory().getItemInHand().getType()){
                //Tp aléatoire

                case ARROW:
                    List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                    list.remove(player);

                    if(list.size() == 0){
                        player.sendMessage("§cIl n'y a aucun joueur sur lequel vous téléporter.");
                        return;
                    }
                    int i = list.size();
                    if(i == 0){
                        i = list.size();
                    }
                    Player target = list.get(i);
                    player.teleport(target.getLocation());
                    player.sendMessage("§aVous avez été téléporté à §e" + target.getName());
                    i--;
                    break;

                //Vanish

                case GLASS:
                    if(Main.getInstance().vanish.containsKey(player.getName())){
                        Main.getInstance().vanish.remove(player.getName());
                        new PlayerManager(player).setVanished();
                        player.sendMessage("§bVous êtes à présent visible !");
                    } else {
                        Main.getInstance().vanish.put(player.getName(), 1);
                        new PlayerManager(player).setVanished();
                        player.sendMessage("§avous êtes à présent invisible !");
                    }
                    break;

                default: break;
            }
        }

    }
}
