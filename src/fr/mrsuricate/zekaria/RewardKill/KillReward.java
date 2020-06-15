package fr.mrsuricate.zekaria.RewardKill;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import fr.mrsuricate.zekaria.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class KillReward implements Listener {

    public static Economy economy = null;

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Main.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null)
            economy = economyProvider.getProvider();
        return (economy != null);
    }

    @EventHandler
    public void onKill(EntityDeathEvent e){
        if(setupEconomy()){
            if(e.getEntity().getKiller() instanceof Player){
                LivingEntity entity = e.getEntity();
                String nameEntity = entity.toString().substring(5);
                if(nameEntity.contains("Ocelot")){
                    nameEntity = "Ocelot";
                }
                if(nameEntity.contains("Wolf")){
                    nameEntity = "Wolf";
                }
                if(nameEntity.contains("Horse")){
                    nameEntity = "Horse";
                }
                if(nameEntity.contains("Rabbit")){
                    nameEntity = "Rabbit";
                }
                int nbr = WildStackerAPI.getEntityAmount(e.getEntity());
                Double dmin = (double) Main.getInstance().getKillRward().get(nameEntity+".min")*100;
                Double dmax = (double) Main.getInstance().getKillRward().get(nameEntity+".max")*100;
                int min = dmin.intValue();
                int max = dmax.intValue();
                Player p = e.getEntity().getKiller();
                int i = 0;
                Random random = new Random();
                int nb;
                nb = min+random.nextInt(max-min);
                double money = nb;
                money = money/100;
                money = money * nbr;
                economy.depositPlayer(p,money);
                p.sendMessage("Vous avez gagnez "+ money+ " ZekaCoins pour avoir tuer " + nbr + " " + nameEntity + ".");
            }

        }
    }


    public void createFile() {
        Main.getInstance().config6 = YamlConfiguration.loadConfiguration(Main.getInstance().Killr);
        Main.getInstance().getKillRward().set("Pig.min",0.5);
        Main.getInstance().getKillRward().set("Pig.max",1.0);
        Main.getInstance().getKillRward().set("Chicken.min",0.5);
        Main.getInstance().getKillRward().set("Chicken.max",1.0);
        Main.getInstance().getKillRward().set("Cow.min",0.5);
        Main.getInstance().getKillRward().set("Cow.max",1.0);
        Main.getInstance().getKillRward().set("Creeper.min",0.5);
        Main.getInstance().getKillRward().set("Creeper.max",1.0);
        Main.getInstance().getKillRward().set("Ghast.min",0.5);
        Main.getInstance().getKillRward().set("Ghast.max",1.0);
        Main.getInstance().getKillRward().set("Giant.min",0.5);
        Main.getInstance().getKillRward().set("Giant.max",1.0);
        Main.getInstance().getKillRward().set("PigZombie.min",0.5);
        Main.getInstance().getKillRward().set("PigZombie.max",1.0);
        Main.getInstance().getKillRward().set("Sheep.min",0.5);
        Main.getInstance().getKillRward().set("Sheep.max",1.0);
        Main.getInstance().getKillRward().set("Skeleton.min",0.5);
        Main.getInstance().getKillRward().set("Skeleton.max",1.0);
        Main.getInstance().getKillRward().set("Slime.min",0.5);
        Main.getInstance().getKillRward().set("Slime.max",1.0);
        Main.getInstance().getKillRward().set("Spider.min",0.5);
        Main.getInstance().getKillRward().set("Spider.max",1.0);
        Main.getInstance().getKillRward().set("Squid.min",0.5);
        Main.getInstance().getKillRward().set("Squid.max",1.0);
        Main.getInstance().getKillRward().set("Zombie.min",0.5);
        Main.getInstance().getKillRward().set("Zombie.max",1.0);
        Main.getInstance().getKillRward().set("Wolf.min",0.5);
        Main.getInstance().getKillRward().set("Wolf.max",1.0);
        Main.getInstance().getKillRward().set("Enderman.min",0.5);
        Main.getInstance().getKillRward().set("Enderman.max",1.0);
        Main.getInstance().getKillRward().set("Silverfish.min",0.5);
        Main.getInstance().getKillRward().set("Silverfish.max",1.0);
        Main.getInstance().getKillRward().set("CaveSpider.min",0.5);
        Main.getInstance().getKillRward().set("CaveSpider.max",1.0);
        Main.getInstance().getKillRward().set("EnderDragon.min",0.5);
        Main.getInstance().getKillRward().set("EnderDragon.max",1.0);
        Main.getInstance().getKillRward().set("Villager.min",0.5);
        Main.getInstance().getKillRward().set("Villager.max",1.0);
        Main.getInstance().getKillRward().set("Blaze.min",0.5);
        Main.getInstance().getKillRward().set("Blaze.max",1.0);
        Main.getInstance().getKillRward().set("MushroomCow.min",0.5);
        Main.getInstance().getKillRward().set("MushroomCow.max",1.0);
        Main.getInstance().getKillRward().set("MagmaCube.min",0.5);
        Main.getInstance().getKillRward().set("MagmaCube.max",1.0);
        Main.getInstance().getKillRward().set("Snowman.min",0.5);
        Main.getInstance().getKillRward().set("Snowman.max",1.0);
        Main.getInstance().getKillRward().set("Ocelot.min",0.5);
        Main.getInstance().getKillRward().set("Ocelot.max",1.0);
        Main.getInstance().getKillRward().set("IronGolem.min",0.5);
        Main.getInstance().getKillRward().set("IronGolem.max",1.0);
        Main.getInstance().getKillRward().set("Wither.min",0.5);
        Main.getInstance().getKillRward().set("Wither.max",1.0);
        Main.getInstance().getKillRward().set("Bat.min",0.5);
        Main.getInstance().getKillRward().set("Bat.max",1.0);
        Main.getInstance().getKillRward().set("Witch.min",0.5);
        Main.getInstance().getKillRward().set("Witch.max",1.0);
        Main.getInstance().getKillRward().set("Endermite.min",0.5);
        Main.getInstance().getKillRward().set("Endermite.max",1.0);
        Main.getInstance().getKillRward().set("Guardian.min",0.5);
        Main.getInstance().getKillRward().set("Guardian.max",1.0);
        Main.getInstance().getKillRward().set("Horse.min",0.5);
        Main.getInstance().getKillRward().set("Horse.max",1.0);
        Main.getInstance().getKillRward().set("Rabbit.min",0.5);
        Main.getInstance().getKillRward().set("Rabbit.max",1.0);
        try {
            Main.getInstance().getKillRward().save(Main.getInstance().Killr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
