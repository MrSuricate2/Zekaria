package fr.mrsuricate.zekaria;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.mrsuricate.zekaria.ChatSide.ChatLock;
import fr.mrsuricate.zekaria.ChatSide.ClearChat;
import fr.mrsuricate.zekaria.CoinFlip.commands.CoinFlipCommand;
import fr.mrsuricate.zekaria.CoinFlip.events.ClickEvent;
import fr.mrsuricate.zekaria.CoinFlip.events.PlayerEvent;
import fr.mrsuricate.zekaria.CoinFlip.utilz.*;
import fr.mrsuricate.zekaria.Cooldownenderpearl.CooldownEnderPearl;
import fr.mrsuricate.zekaria.CustomEnchant.Enchant;
import fr.mrsuricate.zekaria.Décocombats.DécoCombats;
import fr.mrsuricate.zekaria.Décocombats.runnable2;
import fr.mrsuricate.zekaria.HeadDrop.Headdrop;
import fr.mrsuricate.zekaria.RewardKill.KillReward;
import fr.mrsuricate.zekaria.StaffChat.StaffChat;
import fr.mrsuricate.zekaria.TimeIsMoney.TimeisMoney;
import fr.mrsuricate.zekaria.TopFaction.TopFaction;
import fr.mrsuricate.zekaria.Trade.ItemStackUtils;
import fr.mrsuricate.zekaria.Trade.TradeHandler;
import fr.mrsuricate.zekaria.Trade.commands.trade;
import fr.mrsuricate.zekaria.blockCommand.blockCommand;
import fr.mrsuricate.zekaria.commands.*;
import fr.mrsuricate.zekaria.enchere.enchere;
import fr.mrsuricate.zekaria.events.DeathMoney;
import fr.mrsuricate.zekaria.giveall.Giveall;
import fr.mrsuricate.zekaria.moderation.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main extends JavaPlugin implements Listener {

    public static Main getInstance() {
        return JavaPlugin.getPlugin(Main.class);
    }
    //cf
    public static File cf;
    public static FileConfiguration config7;
    private CoinManager coins;
    private StatsManager stats;
    private InventoryManager menu;
    private BroadcastManager broadcast;
    private static Economy econ = null;
    private AnimationManager animation;
    //cf

    //trade
    private TradeHandler tradeHandler;
    public static HashMap<Player, Player> trade = new HashMap<>();
    public static double setmoney;
    public static double setmoney2;
    //trade

    //StaffChat
    public static HashMap<String, Integer> map = new HashMap<>();
    //StaffChat

    //modération
    public static HashMap<String, Integer> modlist = new HashMap<>();
    public static HashMap<String, Float> saturation = new HashMap<>();
    public static HashMap<String, Integer> food = new HashMap<>();
    public static HashMap<String, Double> heal = new HashMap<>();
    public static HashMap<String, Integer> freeze = new HashMap<>();
    public static HashMap<String, Integer> vanish = new HashMap<>();
    public static String reportgetname;
    //modération

    //enchere
    public static int quantité;
    public static Material mat;
    public static double prixDeDepart;
    public static HashMap<Player, ItemStack> data = new HashMap<>();
    public static byte enchereEnCours;
    public static HashMap<Player, Double> bid = new HashMap<>();
    public static double bidup;
    public static Player lastbid;
    public static Player namecreate;
    public static MaterialData item2;
    public static short dura;
    public static ItemMeta meta;
    public static ItemStack is;
    public static String itemJson;
    //enchere

    //déco-combats
    public static File Déco_Combats;
    public static FileConfiguration config3;
    public WorldGuardPlugin WorldGuard;
    //déco-combats

    //TimeIsMoney
    public static File TimeIsMoney;
    public static FileConfiguration config2;
    public static HashMap<String, Integer> onlinePlayer = new HashMap<>();
    //TimeIsMoney

    //CustomEnchant
    public static HashMap<Player, Integer> enchant = new HashMap<>();
    //CustomEnchant

    //cooldown_enderpearl
    public static File Cenderpearl;
    public static FileConfiguration config5;
    //cooldown_enderpearl

    //top faction
    private boolean useHolographicDisplays;
    //top faction

    //Reward kill
    public static File Killr;
    public static FileConfiguration config6;
    //Reward kill

    //potion
    public static File potion;
    public static FileConfiguration config8;
    //potion

    //Bienvenue
    public static File NewPlayer;
    public static FileConfiguration config9;
    //Bienvenue

    public static boolean chatlock = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
        getCommand("warp").setExecutor(new Warp());
        getServer().getPluginManager().registerEvents(new Warp(), this);
        getCommand("alert").setExecutor(new alert());
        getCommand("discord").setExecutor(new discord());
        getCommand("ts").setExecutor(new discord());
        getCommand("site").setExecutor(new discord());
        this.NewPlayer = new File(getDataFolder() + File.separator + "NewPlayer.yml");
        if(!NewPlayer.exists()){
            try{
                NewPlayer.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        this.config9 = YamlConfiguration.loadConfiguration(NewPlayer);
        getServer().getPluginManager().registerEvents(new bienvenue(), this);
        getCommand("bienvenue").setExecutor(new bienvenue());
        new bienvenue().runTaskTimer(Main.getInstance(),0L, 20L);
        getServer().getPluginManager().registerEvents(new DeathMoney(), this);
        //TODO getServer().getPluginManager().registerEvents(new EffectBlood(), this);



        //modération
        getCommand("moderation").setExecutor(new moderation());
        getCommand("report").setExecutor(new moderation());
        getServer().getPluginManager().registerEvents(new moderation(), this);
        getServer().getPluginManager().registerEvents(new ModCancels(), this);
        getServer().getPluginManager().registerEvents(new ModsItemsInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        //modération

        getCommand("giveall").setExecutor(new Giveall());
        //Chat Side
        getCommand("clearchat").setExecutor(new ClearChat());
        getCommand("chatlock").setExecutor(new ChatLock());
        getServer().getPluginManager().registerEvents(new ChatLock(), this);
        getCommand("localclear").setExecutor(new ClearChat());
        //Chat Side
        //trade
        getCommand("trade").setExecutor(new trade());
        getServer().getPluginManager().registerEvents(this.tradeHandler = new TradeHandler(), this);
        ItemStackUtils.loadUtils();
        //trade
        //cf
        this.cf = new File(getDataFolder() + File.separator + "CoinFlip.yml");
        if(!cf.exists()){
            try{
                cf.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        this.config7 = YamlConfiguration.loadConfiguration(cf);
        this.broadcast = new BroadcastManager();
        this.stats = new StatsManager();
        this.coins = new CoinManager();
        this.menu = new InventoryManager();
        this.animation = new AnimationManager();
        getCommand("coinflip").setExecutor(new CoinFlipCommand());
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerEvent(), this);
        setupEconomy();
        //cf


        //StaffChat
        getCommand("staffchat").setExecutor(new StaffChat());
        Bukkit.getPluginManager().registerEvents(new StaffChat(), this);
        //StaffChat


        //enchere
        getCommand("enchere").setExecutor(new enchere());
        //enchere
        //timemoney
        //Rules
        getCommand("rules").setExecutor(new Rules());
        //Fin Rules
        //HeadDrop
        Bukkit.getPluginManager().registerEvents(new Headdrop(), this);
        //Fin HeadDrop
        this.TimeIsMoney = new File(getDataFolder() + File.separator + "TimeIsMoney.yml");
        if(!TimeIsMoney.exists()){
            try{
                TimeIsMoney.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        this.config2 = YamlConfiguration.loadConfiguration(TimeIsMoney);
        Bukkit.getPluginManager().registerEvents(new TimeisMoney(), this);
        //timemoney
        //décocombats
        this.Déco_Combats = new File(getDataFolder() + File.separator + "Deco_combats.yml");
        if(!Déco_Combats.exists()){
            try{
                Déco_Combats.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        this.config3 = YamlConfiguration.loadConfiguration(Déco_Combats);
        WorldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        Bukkit.getPluginManager().registerEvents(new DécoCombats(), this);
        new runnable2().runTaskTimer(Main.getInstance(),0L, 20L);
        //décocombats


        //CustomEnchant
        Bukkit.getPluginManager().registerEvents(new Enchant(), this);
        new Enchant().runTaskTimer(Main.getInstance(),0L, 20L);
        getCommand("CustomEnchant").setExecutor(new Enchant());
        //CustomEnchant


        //BlockCommand
        Bukkit.getPluginManager().registerEvents(new blockCommand(), this);
        //BlockCommand


        //cooldown_enderpearl
        this.Cenderpearl = new File(getDataFolder() + File.separator + "Cooldown enderpearl.yml");
        if(!Cenderpearl.exists()){
            try{
                Cenderpearl.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        this.config5 = YamlConfiguration.loadConfiguration(Cenderpearl);
        Bukkit.getPluginManager().registerEvents(new CooldownEnderPearl(), this);
        new CooldownEnderPearl().runTaskTimer(Main.getInstance(),0L, 20L);
        //cooldown_enderpearl


        //top faction
        useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
        Bukkit.getPluginManager().registerEvents(new TopFaction(), this);
        getCommand("faction").setExecutor(new TopFaction());
        new TopFaction().runTaskTimer(Main.getInstance(),0L, 200L);
        //top faction

        //Reward kill
        this.Killr = new File(getDataFolder() + File.separator + "Kill Reward.yml");
        if(!Killr.exists()){
            try{
                Killr.createNewFile();
                new KillReward().createFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        this.config6 = YamlConfiguration.loadConfiguration(Killr);
        Bukkit.getPluginManager().registerEvents(new KillReward(), this);
        new KillReward().runTaskTimer(Main.getInstance(),0L, 200L);
        //Reward kill

        //shop potion
        this.potion = new File(getDataFolder() + File.separator + "Potion.yml");
        if(!potion.exists()){
            try{
                potion.createNewFile();
                new KillReward().createFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        this.config8 = YamlConfiguration.loadConfiguration(potion);
        getCommand("zekaria").setExecutor(new Zekaria());
        new Zekaria().runTaskTimer(Main.getInstance(),0L, 20L);
        //shop potion



    }

    //cf
    public FileConfiguration getcf(){
        return config7;
    }

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
    public TradeHandler getTradeHandler() {
        return this.tradeHandler;
    }
    //trade

    //TimeIsMoney
    public FileConfiguration getTimeIsMoneyConfig(){
        return config2;
    }
    //TimeIsMoney

    //Déco-combats
    public FileConfiguration getDéco_cobatsConfig(){
        return config3;
    }
    //Déco-combats

    //cooldown_enderpearl
    public FileConfiguration getCenderPearl(){
        return config5;
    }
    //cooldown_enderpearl

    //Reward kill
    public FileConfiguration getKillRward(){
        return config6;
    }
    //Reward kill

    //potion
    public FileConfiguration getpotion(){
        return config8;
    }
    //potion

    //Bienvenue
    public FileConfiguration getNewPlayer(){
        return config9;
    }
    //Bienvenue

    @Override
    public void onDisable() {

    }

}
