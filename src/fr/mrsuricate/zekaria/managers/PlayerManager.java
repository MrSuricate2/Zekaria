package fr.mrsuricate.zekaria.managers;

import fr.mrsuricate.zekaria.Main;
import fr.mrsuricate.zekaria.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {

    private Player player;
    private ItemStack[] items = new ItemStack[40];
    private boolean vanished;

    public PlayerManager(Player player){
        this.player = player;
        vanished = false;
    }

    public void init(){
        Main.getInstance().players.put(player.getUniqueId(), this);
        Main.getInstance().moderateurs.add(player.getUniqueId());
        player.sendMessage("§aVous êtes à présent en mode modération");
        saveInventory();
        player.setAllowFlight(true);
        player.setFlying(true);

        ItemBuilder invSee = new ItemBuilder(Material.PAPER).setName("§eVoir l'inventaire").setLore("§6Clique droit sur un joueur", "§6pour voir son inventaire.");
        ItemBuilder reports = new ItemBuilder(Material.BOOK).setName("§eVoir les signalements").setLore("§6Clique droit sur un joueur", "§6pour voir ses signalements.");
        ItemBuilder freeze = new ItemBuilder(Material.PACKED_ICE).setName("§eFreeze").setLore("§6Clique droit sur un joueur", "§6pour le freeze.");
        ItemBuilder kbTester = new ItemBuilder(Material.STICK).setName("§eTest de recul").setLore("§6Clique gauche sur un joueur", "§6pour tester son recul.").addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
        ItemBuilder killer = new ItemBuilder(Material.BLAZE_ROD).setName("§eTueur de joueur").setLore("§6Clique droit sur un joueur", "§6pour le tuer.");
        ItemBuilder tpRandom = new ItemBuilder(Material.ARROW).setName("§eTéléportation aléatoire").setLore("§6Clique droit pour se téléporter", "§6aléatoirement sur un joueur.");
        ItemBuilder vanish = new ItemBuilder(Material.BLAZE_POWDER).setName("§eVanish").setLore("§6Clique droit pour activer/désactiver", "§6le vanish.");

        player.getInventory().setItem(0, invSee.toItemStack());
        player.getInventory().setItem(1, reports.toItemStack());
        player.getInventory().setItem(2, freeze.toItemStack());
        player.getInventory().setItem(3, kbTester.toItemStack());
        player.getInventory().setItem(4, killer.toItemStack());
        player.getInventory().setItem(5, tpRandom.toItemStack());
        player.getInventory().setItem(6, vanish.toItemStack());
    }

    public void destroy(){
        Main.getInstance().players.remove(player.getUniqueId());
        Main.getInstance().moderateurs.remove(player.getUniqueId());
        player.getInventory().clear();
        player.sendMessage("§cVous n'êtes maintenant plus en mode modération");
        giveInventory();
        player.setAllowFlight(false);
        player.setFlying(false);
        setVanished(false);
    }

    public static PlayerManager getFromPlayer(Player player){
        return Main.getInstance().players.get(player.getUniqueId());
    }

    public static boolean isInModerationMod(Player player){
        return Main.getInstance().moderateurs.contains(player.getUniqueId());
    }

    public ItemStack[] getItems() {
        return items;
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished){
        this.vanished = vanished;
        if(vanished){
            Bukkit.getOnlinePlayers().forEach(players -> players.hidePlayer(player));
        } else {
            Bukkit.getOnlinePlayers().forEach(players -> players.showPlayer(player));
        }
    }

    public void saveInventory(){
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
    }

    public void giveInventory(){
        player.getInventory().clear();

        for(int slot = 0; slot < 36; slot++){
            ItemStack item = items[slot];
            if(item != null){
                player.getInventory().setItem(slot, item);
            }
        }

        player.getInventory().setHelmet(items[36]);
        player.getInventory().setChestplate(items[37]);
        player.getInventory().setLeggings(items[38]);
        player.getInventory().setBoots(items[39]);
    }
}
