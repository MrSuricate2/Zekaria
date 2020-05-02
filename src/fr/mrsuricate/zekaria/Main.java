package fr.mrsuricate.zekaria;

import fr.mrsuricate.zekaria.commands.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    public static Main getInstance() {
        return JavaPlugin.getPlugin(Main.class);
    }

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        getCommand("alert").setExecutor(new alert());
        getCommand("discord").setExecutor(new discord());
        getCommand("spawners").setExecutor(new spawner());
        getServer().getPluginManager().registerEvents(new spawner(), this);
        getCommand("grades").setExecutor(new grades());
        getCommand("buykits").setExecutor(new buykit());

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
