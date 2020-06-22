package fr.mrsuricate.zekaria.moderation;

import fr.mrsuricate.zekaria.Main;
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
        if(Main.getInstance().modlist.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
        if (Main.getInstance().freeze.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(Main.getInstance().modlist.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
        if (Main.getInstance().freeze.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e){
        if(Main.getInstance().modlist.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
        if (Main.getInstance().freeze.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e){
        if(Main.getInstance().modlist.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
        if (Main.getInstance().freeze.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(Main.getInstance().modlist.containsKey(e.getEntity().getName())){
            e.setCancelled(true);
        }
        if (Main.getInstance().freeze.containsKey(e.getEntity().getName())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(Main.getInstance().modlist.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
        if (Main.getInstance().freeze.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(EntityDamageByEntityEvent e){
        if(Main.getInstance().modlist.containsKey(e.getEntity().getName())){
            e.setCancelled(true);
        }
        if (Main.getInstance().freeze.containsKey(e.getEntity().getName())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (Main.getInstance().freeze.containsKey(e.getPlayer().getName())){
            e.setTo(e.getFrom());
        }
    }
}
