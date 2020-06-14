package fr.mrsuricate.zekaria.moderation;

import fr.mrsuricate.zekaria.Main;
import fr.mrsuricate.zekaria.moderation.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ModCancels implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        e.setCancelled(PlayerManager.isInModerationMod(e.getPlayer()) || Main.getInstance().isFreeze(e.getPlayer()));
    }
/*
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        e.setCancelled(PlayerManager.isInModerationMod(e.getPlayer()) || Main.getInstance().isFreeze(e.getPlayer()));
    }
*/
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e){
        e.setCancelled(PlayerManager.isInModerationMod(e.getPlayer()) || Main.getInstance().isFreeze(e.getPlayer()));
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e){
        e.setCancelled(PlayerManager.isInModerationMod(e.getPlayer()) || Main.getInstance().isFreeze(e.getPlayer()));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        e.setCancelled(PlayerManager.isInModerationMod((Player) e.getEntity()) || Main.getInstance().isFreeze((Player) e.getEntity()));
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if (PlayerManager.isInModerationMod(e.getPlayer())){
            e.setCancelled(true);
        }
        if (Main.getInstance().isFreeze(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getDamager() instanceof Player)) return;
        Player damager = (Player) e.getDamager();
        if(PlayerManager.isInModerationMod(damager)){
            e.setCancelled(damager.getInventory().getItemInHand().getType() != Material.STICK);
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(Main.getInstance().isFreeze(e.getPlayer())){
            e.setTo(e.getFrom());
        }
    }
}
