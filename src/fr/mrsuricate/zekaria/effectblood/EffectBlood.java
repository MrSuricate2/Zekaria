package fr.mrsuricate.zekaria.effectblood;

import fr.mrsuricate.zekaria.Main;
//import net.minecraft.server.v1_12_R1.EnumParticle;
//import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
//import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
//import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EffectBlood implements Listener {

    public Material block;

    @EventHandler
    public void onEffetSang(EntityDamageByEntityEvent e) {
        this.block = Material.getMaterial(Main.getInstance().getConfig().getString("block"));
        if (this.block != null) {
            Entity entity = e.getEntity();
            Location entityloc = entity.getLocation();
            if (entity.getType() != EntityType.ITEM_FRAME && entity.getType() != EntityType.ARMOR_STAND) {
                entity.getWorld().playEffect(entityloc, Effect.STEP_SOUND, this.block);
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(Main.getInstance().getConfig().getString("messages.error-block"));
        }
    }

}
