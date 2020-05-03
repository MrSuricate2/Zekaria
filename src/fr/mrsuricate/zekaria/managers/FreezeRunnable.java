package fr.mrsuricate.zekaria.managers;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class FreezeRunnable extends BukkitRunnable {

    private int t = 0;

    @Override
    public void run() {
        for(UUID uuid : Main.getInstance().freezedPlayers.keySet()){
            Player player = Bukkit.getPlayer(uuid);
            if(player != null){
                player.teleport(Main.getInstance().freezedPlayers.get(uuid));
                if(t == 5){
                    player.sendMessage("");
                    player.sendMessage("§6Merci de venir discord: §bhttps://discord.gg/knXYnBG");
                    player.sendMessage("");
                }
            }
        }
        if(t == 5){
                t = 0;

        }

        t++;
    }
}
