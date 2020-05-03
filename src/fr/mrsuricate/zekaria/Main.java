package fr.mrsuricate.zekaria;

import fr.mrsuricate.zekaria.commands.*;
import fr.mrsuricate.zekaria.effectblood.EffectBlood;
import fr.mrsuricate.zekaria.events.DeathMoney;
import fr.mrsuricate.zekaria.events.ModCancels;
import fr.mrsuricate.zekaria.events.ModsItemsInteract;
import fr.mrsuricate.zekaria.events.PlayerLeave;
import fr.mrsuricate.zekaria.managers.FreezeRunnable;
import fr.mrsuricate.zekaria.managers.PlayerManager;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {

    public static Main getInstance() {
        return JavaPlugin.getPlugin(Main.class);
    }
    public ArrayList<UUID> moderateurs = new ArrayList<>();
    public HashMap<UUID, PlayerManager> players = new HashMap<>();
    public HashMap<UUID, Location> freezedPlayers = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        PluginManager pm = getServer().getPluginManager();
        getCommand("alert").setExecutor(new alert());
        getCommand("discord").setExecutor(new discord());
        getCommand("spawners").setExecutor(new spawner());
        getServer().getPluginManager().registerEvents(new spawner(), this);
        getCommand("grades").setExecutor(new grades());
        getServer().getPluginManager().registerEvents(new grades(), this);
        getCommand("buykits").setExecutor(new buykit());
        getServer().getPluginManager().registerEvents(new buykit(), this);
        getServer().getPluginManager().registerEvents(new DeathMoney(), this);
        //TODO getServer().getPluginManager().registerEvents(new EffectBlood(), this);
        getCommand("moderation").setExecutor(new moderation());
        getCommand("report").setExecutor(new moderation());
        getServer().getPluginManager().registerEvents(new moderation(), this);
        getServer().getPluginManager().registerEvents(new ModCancels(), this);
        getServer().getPluginManager().registerEvents(new ModsItemsInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        new FreezeRunnable().runTaskTimer(this, 0, 20);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
