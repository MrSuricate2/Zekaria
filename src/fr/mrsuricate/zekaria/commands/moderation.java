package fr.mrsuricate.zekaria.commands;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

        Player p = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("moderation")){
            if(!p.hasPermission("mod.interface.use")){
                p.sendMessage("§4Vous n'avez pas la permission !");
                return false;
            }

            if (Main.getInstance().moderateurs.contains(p.getUniqueId())){
                Main.getInstance().moderateurs.remove(p.getUniqueId());
                p.getInventory().clear();
                p.sendMessage("§4Vous n'êtes plus dans le mode modération");

                return false;
            }

            Main.getInstance().moderateurs.add(p.getUniqueId());
            p.sendMessage("§6Vous êtes à present dans le mode modération");


        }

        if(cmd.getName().equalsIgnoreCase("report")){
            if(args.length != 1){
                p.sendMessage("§4§lMerci de préciser le pseudo d'un joueur");
                return false;
            }

            String reportedname = args[0];

            if(Bukkit.getPlayer(reportedname) == null){
                p.sendMessage("§4§lCe joueur n'est pas connécté !");
                return false;
            }

            Player reported = Bukkit.getPlayer(reportedname);

            Inventory inv = Bukkit.createInventory(null, 18, "§4Report: §2" + reportedname);

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

            p.openInventory(inv);

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
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), e.getInventory().getName().substring(12));
                    p.sendMessage("§2Le joueur a bien été signalé !");
                }
                break;
            case BOW:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cSpamBow")){
                    e.setCancelled(true);
                    p.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), e.getInventory().getName().substring(12));
                    p.sendMessage("§2Le joueur a bien été signalé !");
                }
                break;
        }

    }

    private void sendToMods(String reason, String reportedname) {
        for(Player players : Bukkit.getOnlinePlayers()){
            if(players.hasPermission("mod.receive.use")){
                players.sendMessage("§bLe joueur §a" + reportedname + " §ba été signalé pour : " + reason);
            }
        }
    }
}
