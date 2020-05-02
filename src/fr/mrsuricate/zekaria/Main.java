package fr.mrsuricate.zekaria;

import fr.mrsuricate.zekaria.commands.*;
import fr.mrsuricate.zekaria.effectblood.EffectBlood;
import fr.mrsuricate.zekaria.events.DeathMoney;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    public static Main getInstance() {
        return JavaPlugin.getPlugin(Main.class);
    }

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
        getServer().getPluginManager().registerEvents(new EffectBlood(), this);


    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
