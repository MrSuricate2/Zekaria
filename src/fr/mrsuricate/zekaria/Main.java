package fr.mrsuricate.zekaria;

import fr.mrsuricate.zekaria.CoinFlip.commands.CoinFlipCommand;
import fr.mrsuricate.zekaria.CoinFlip.events.ClickEvent;
import fr.mrsuricate.zekaria.CoinFlip.events.PlayerQuitEvent;
import fr.mrsuricate.zekaria.CoinFlip.utilz.*;
import fr.mrsuricate.zekaria.Trade.ItemStackUtils;
import fr.mrsuricate.zekaria.Trade.TradeHandler;
import fr.mrsuricate.zekaria.Trade.commands.trade;
import fr.mrsuricate.zekaria.commands.*;
import fr.mrsuricate.zekaria.events.DeathMoney;
import fr.mrsuricate.zekaria.giveall.Giveall;
import fr.mrsuricate.zekaria.moderation.ModCancels;
import fr.mrsuricate.zekaria.moderation.ModsItemsInteract;
import fr.mrsuricate.zekaria.moderation.PlayerLeave;
import fr.mrsuricate.zekaria.moderation.PlayerManager;
import fr.mrsuricate.zekaria.moderation.moderation;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {

    public static Main getInstance() {
        return JavaPlugin.getPlugin(Main.class);
    }
    //cf
    private CoinManager coins;
    private StatsManager stats;
    private InventoryManager menu;
    private BroadcastManager broadcast;
    private static Economy econ = null;
    private AnimationManager animation;
    //cf

    //trade
    private TradeHandler tradeHandler;
    //trade

    public ArrayList<UUID> moderateurs = new ArrayList<>();
    public HashMap<UUID, PlayerManager> players = new HashMap<>();
    public HashMap<UUID, Location> freezedPlayers = new HashMap<>();
    public boolean isFreeze(Player player) {return freezedPlayers.containsKey(player.getUniqueId());}

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
        getCommand("giveall").setExecutor(new Giveall());

        //trade
        getCommand("trade").setExecutor(new trade());
        getServer().getPluginManager().registerEvents(this.tradeHandler = new TradeHandler(), this);
        ItemStackUtils.loadUtils();
        //trade


        //cf
        this.broadcast = new BroadcastManager();
        this.stats = new StatsManager();
        this.coins = new CoinManager();
        this.menu = new InventoryManager();
        this.animation = new AnimationManager();
        getCommand("coinflip").setExecutor(new CoinFlipCommand());
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this);
        setupEconomy();
        //cf


    }

    //cf
    public BroadcastManager getBroadcast() {
        return this.broadcast;
    }

    public StatsManager getStats() {
        return this.stats;
    }

    public CoinManager getCoins() {
        return this.coins;
    }

    public Inventory getMenu() {
        return this.menu.getMenu();
    }

    public AnimationManager getAnimation() {
        return this.animation;
    }

    public InventoryManager getMenuManager() {
        return this.menu;
    }

    public static Economy getEconomy() {
        return econ;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        econ = rsp.getProvider();
        return (econ != null);
    }
    //cf

    //trade

    //trade




    @Override
    public void onDisable() {
        super.onDisable();
    }

    public TradeHandler getTradeHandler() {
        return this.tradeHandler;
    }
}
