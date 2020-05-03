package fr.mrsuricate.zekaria.commands;

import fr.mrsuricate.zekaria.Main;
import fr.mrsuricate.zekaria.managers.PlayerManager;
import fr.mrsuricate.zekaria.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class moderation implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Uniquement un joueur peut utiliser cette commande");
            return false;
        }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("moderation")){
            if(!player.hasPermission("moderation.mod")){
                player.sendMessage("§cVous n'avez pas la permission !");
                return false;
            }

            if(PlayerManager.isInModerationMod(player)){
                PlayerManager pm = PlayerManager.getFromPlayer(player);

                Main.getInstance().moderateurs.remove(player.getUniqueId());
                player.getInventory().clear();
                player.sendMessage("§cVous n'êtes maintenant plus en mode modération");
                pm.giveInventory();
                pm.destroy();
                player.setAllowFlight(false);
                player.setFlying(false);
                return false;
            }

            PlayerManager pm = new PlayerManager(player);
            pm.init();

            Main.getInstance().moderateurs.add(player.getUniqueId());
            player.sendMessage("§aVous êtes à présent en mode modération");
            pm.saveInventory();
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

        if(cmd.getName().equalsIgnoreCase("report")){
            if(args.length != 1){
                player.sendMessage("§4§lMerci de préciser le pseudo d'un joueur");
                return false;
            }

            String reportedname = args[0];

            if(Bukkit.getPlayer(reportedname) == null){
                player.sendMessage("§4§lCe joueur n'est pas connécté !");
                return false;
            }

            Inventory inv = Bukkit.createInventory(null, 9, "§4Report: §2" + reportedname);

            //Type de hack

            //ForceField
            ItemStack forcefield = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta forcefieldM = forcefield.getItemMeta();
            forcefieldM.setDisplayName("§cForceField");
            forcefield.setItemMeta(forcefieldM);
            inv.setItem(0, forcefield);

            //SpamBow
            ItemStack spambow = new ItemStack(Material.BOW);
            ItemMeta spambowM = spambow.getItemMeta();
            spambowM.setDisplayName("§cSpamBow");
            spambow.setItemMeta(spambowM);
            inv.setItem(1, spambow);

            //AntiKnockBack
            ItemStack antiknockback = new ItemStack(Material.STICK);
            ItemMeta antiknockbackM = antiknockback.getItemMeta();
            antiknockbackM.setDisplayName("§cAnti-KB");
            antiknockback.setItemMeta(antiknockbackM);
            inv.setItem(2, antiknockback);

            //Blink
            ItemStack blink = new ItemStack(Material.ENDER_PEARL);
            ItemMeta blinkM = blink.getItemMeta();
            blinkM.setDisplayName("§cBlink");
            blink.setItemMeta(blinkM);
            inv.setItem(3, blink);

            //Spider
            ItemStack spider = new ItemStack(Material.SPIDER_EYE);
            ItemMeta spiderM = spider.getItemMeta();
            spiderM.setDisplayName("§cClimb / Spider");
            spider.setItemMeta(spiderM);
            inv.setItem(4, spider);

            //Criticals
            ItemStack criticals = new ItemStack(Material.BARRIER);
            ItemMeta criticalsM = criticals.getItemMeta();
            criticalsM.setDisplayName("§cCriticals");
            criticals.setItemMeta(criticalsM);
            inv.setItem(5, criticals);

            //Dolphin
            ItemStack dolphin = new ItemStack(Material.PACKED_ICE);
            ItemMeta dolphinM = dolphin.getItemMeta();
            dolphinM.setDisplayName("§cDolphin");
            dolphin.setItemMeta(dolphinM);
            inv.setItem(6, dolphin);

            //Nuker
            ItemStack nuker = new ItemStack(Material.TNT);
            ItemMeta nukerM = nuker.getItemMeta();
            nukerM.setDisplayName("§cNuker");
            nuker.setItemMeta(nukerM);
            inv.setItem(7, nuker);

            //Reach
            ItemStack reach = new ItemStack(Material.ARROW);
            ItemMeta reachM = reach.getItemMeta();
            reachM.setDisplayName("§cReach");
            reach.setItemMeta(reachM);
            inv.setItem(8, reach);

            player.openInventory(inv);

            return true;
        }

        return false;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;

        Player p = (Player) e.getWhoClicked();

        switch (e.getCurrentItem().getType()){

            case DIAMOND_SWORD:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cForceField")){
                    e.setCancelled(true);
                    p.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), p.getName(), e.getInventory().getName().substring(12));
                    p.sendMessage("§2Le joueur a bien été signalé !");
                }
                break;
            case BOW:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cSpamBow")){
                    e.setCancelled(true);
                    p.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), p.getName(), e.getInventory().getName().substring(12));
                    p.sendMessage("§2Le joueur a bien été signalé !");
                }
                break;
            case STICK:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cAnti-KB")){
                    e.setCancelled(true);
                    p.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), p.getName(), e.getInventory().getName().substring(12));
                    p.sendMessage("§2Le joueur a bien été signalé");
                }
                break;
            case ENDER_PEARL:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cBlink")){
                    e.setCancelled(true);
                    p.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), p.getName(), e.getInventory().getName().substring(12));
                    p.sendMessage("§2Le joueur a bien été signalé");
                }
                break;
            case SPIDER_EYE:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cClimb / Spider")){
                    e.setCancelled(true);
                    p.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), p.getName(), e.getInventory().getName().substring(12));
                    p.sendMessage("§2Le joueur a bien été signalé");
                }
                break;
            case BARRIER:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cCriticals")){
                    e.setCancelled(true);
                    p.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), p.getName(), e.getInventory().getName().substring(12));
                    p.sendMessage("§2Le joueur a bien été signalé");
                }
                break;
            case PACKED_ICE:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cDolphin")){
                    e.setCancelled(true);
                    p.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), p.getName(), e.getInventory().getName().substring(12));
                    p.sendMessage("§2Le joueur a bien été signalé");
                }
                break;
            case TNT:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cNuker")){
                    e.setCancelled(true);
                    p.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), p.getName(), e.getInventory().getName().substring(12));
                    p.sendMessage("§2Le joueur a bien été signalé");
                }
                break;
            case ARROW:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cReach")){
                    e.setCancelled(true);
                    p.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), p.getName(), e.getInventory().getName().substring(12));
                    p.sendMessage("§2Le joueur a bien été signalé");
                }
                break;
        }

    }

    private void sendToMods(String reason, String reportedname, String authorname) {
        for(Player players : Bukkit.getOnlinePlayers()){
            if(players.hasPermission("mod.receive.use")){
                players.sendMessage("§8§l[§4§lReport§8§l]§r §bLe joueur §r§a" + reportedname + " §ba été signalé par §a" + authorname + " §bpour : §c" + reason);
            }
        }
    }
}
