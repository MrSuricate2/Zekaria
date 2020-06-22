package fr.mrsuricate.zekaria.moderation;

import fr.mrsuricate.zekaria.Main;
import fr.mrsuricate.zekaria.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;

public class PlayerManager {

    private Player player;
    private static HashMap<String, ItemStack[]> inventaire = new HashMap<String, ItemStack[]>();

    public PlayerManager(Player player){
        this.player = player;
    }

    public void init(){
        player.sendMessage("§aVous êtes à présent en mode modération");
        saveInventory();
        player.setAllowFlight(true);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 1, false, false));

        ItemBuilder invSee = new ItemBuilder(Material.PAPER).setName("§eVoir l'inventaire").setLore("§6Clique droit sur un joueur", "§6pour voir son inventaire.");
        ItemBuilder reports = new ItemBuilder(Material.BOOK).setName("§eVoir les signalements").setLore("§6Clique droit sur un joueur", "§6pour voir ses signalements.");
        ItemBuilder freeze = new ItemBuilder(Material.PACKED_ICE).setName("§eFreeze").setLore("§6Clique droit sur un joueur", "§6pour le freeze.");
        ItemBuilder kbTester = new ItemBuilder(Material.STICK).setName("§eTest de recul").setLore("§6Clique gauche sur un joueur", "§6pour tester son recul.").addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
        ItemBuilder killer = new ItemBuilder(Material.BLAZE_ROD).setName("§eTueur de joueur").setLore("§6Clique droit sur un joueur", "§6pour le tuer.");
        ItemBuilder tpRandom = new ItemBuilder(Material.ARROW).setName("§eTéléportation aléatoire").setLore("§6Clique droit pour se téléporter", "§6aléatoirement sur un joueur.");
        ItemBuilder vanish = new ItemBuilder(Material.GLASS).setName("§eVanish").setLore("§6Clique droit pour activer/désactiver", "§6le vanish.");

        player.getInventory().setItem(0, invSee.toItemStack());
        player.getInventory().setItem(1, reports.toItemStack());
        player.getInventory().setItem(2, freeze.toItemStack());
        player.getInventory().setItem(3, kbTester.toItemStack());
        player.getInventory().setItem(4, killer.toItemStack());
        player.getInventory().setItem(5, tpRandom.toItemStack());
        player.getInventory().setItem(6, vanish.toItemStack());
    }

    public void destroy(){
        player.getInventory().clear();
        player.sendMessage("§cVous n'êtes maintenant plus en mode modération");
        giveInventory(player.getName());
        player.setAllowFlight(false);
        player.setFlying(false);
        setVanished();
        if(player.getGameMode() == GameMode.SPECTATOR){
            player.setGameMode(GameMode.SURVIVAL);
        }
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

    public void setVanished(){
        if(Main.getInstance().vanish.containsKey(player.getName())){
            Bukkit.getOnlinePlayers().forEach(players -> players.hidePlayer(player));
        } else {
            Bukkit.getOnlinePlayers().forEach(players -> players.showPlayer(player));
        }
    }

    public void saveInventory(){
        ItemStack[] items = new ItemStack[40];

        for(int slot = 0; slot < 36; slot++){
            ItemStack item = player.getInventory().getItem(slot);
            if(item != null){
                items[slot] = item;
            }
        }

        items[36] = player.getInventory().getHelmet();
        items[37] = player.getInventory().getChestplate();
        items[38] = player.getInventory().getLeggings();
        items[39] = player.getInventory().getBoots();

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        inventaire.put(player.getName(), items);
    }

    public void giveInventory(String name){
        ItemStack[] it = inventaire.get(name);
        for(int slot = 0; slot < 36; slot++){
            ItemStack item = it[slot];
            if(item != null){
                player.getInventory().setItem(slot, item);
            }
        }

        player.getInventory().setHelmet(it[36]);
        player.getInventory().setChestplate(it[37]);
        player.getInventory().setLeggings(it[38]);
        player.getInventory().setBoots(it[39]);
        inventaire.remove(name);
    }
}
