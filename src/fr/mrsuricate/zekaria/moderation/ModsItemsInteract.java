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
        if(!PlayerManager.isInModerationMod(player)) return;
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
                if(Main.getInstance().freezedPlayers.containsKey(target.getUniqueId())){
                    Main.getInstance().freezedPlayers.remove(target.getUniqueId());
                    target.sendMessage("§6Vous avez été unfreeze par §b" + player.getName());
                    player.sendMessage("§6Vous avez unfreeze §b" + target.getName());
                } else {
                    Main.getInstance().freezedPlayers.put(target.getUniqueId(), target.getLocation());
                    target.sendMessage("§6Vous avez été freeze par §b" + player.getName());
                    player.sendMessage("§6Vous avez freeze §b" + target.getName());
                }
                break;

            //Kill player
            case BLAZE_ROD:
                target.damage(target.getHealth());
                break;

            default: break;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(!PlayerManager.isInModerationMod(player)) return;
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

                Player target = list.get(new Random().nextInt(list.size()));
                player.teleport(target.getLocation());
                player.sendMessage("§aVous avez été téléporté à §e" + target.getName());
                break;

            //Vanish

            case GLASS:
                PlayerManager mod = PlayerManager.getFromPlayer(player);
                mod.setVanished(!mod.isVanished());
                player.sendMessage(mod.isVanished() ? "§avous êtes à présent invisible !" : "§bVous êtes à présent visible !");
                break;

            default: break;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        for(Player players : Bukkit.getOnlinePlayers()){
            if(PlayerManager.isInModerationMod(players)){
                PlayerManager pm = PlayerManager.getFromPlayer(players);
                if(pm.isVanished()){
                    player.hidePlayer(players);
                }
            }
        }
    }
}
