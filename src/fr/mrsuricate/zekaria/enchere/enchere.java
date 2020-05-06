package fr.mrsuricate.zekaria.enchere;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class enchere implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            if(cmd.getName().equalsIgnoreCase("enchere")){
                if(sender.hasPermission("enchere.use")){
                    if(args.length == 0){
                        sender.sendMessage("/enchere create <quantité> <prix>");
                    }
                    if(args.length >= 1){
                        if(args[0].equalsIgnoreCase("create")){
                            if(args.length >= 2){
                                try{
                                    Main.getInstance().quantité = Integer.parseInt(args[1]);
                                } catch (Exception e){
                                    sender.sendMessage("§cMauvaise quantité");
                                    Main.getInstance().quantité = 0;
                                }
                                if(Main.getInstance().quantité <= ((Player)sender).getItemInHand().getAmount() && Main.getInstance().quantité!= 0){
                                    if(args.length >= 3){
                                        try{
                                            Main.getInstance().prixDeDepart = Double.parseDouble(args[2]);
                                        } catch (Exception e){
                                            sender.sendMessage("§cMauvais prix");
                                            Main.getInstance().prixDeDepart = 0;
                                        }
                                        if (Main.getInstance().prixDeDepart >= 1){
                                            Player name = (Player) sender;
                                            if(!Main.getInstance().data.containsKey(name)){
                                                if(Main.getInstance().enchereEnCours == 0){
                                                    sender.sendMessage("§aVotre enchere à bien était crée !");
                                                    enchereManager((Player) sender);
                                                } else {
                                                    sender.sendMessage("§aVeuillez attendre que l'enchere soit fini !");
                                                }
                                            } else {
                                                sender.sendMessage("§aUne enchere à vous est déjà en cours!");
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            sender.sendMessage("/enchere create <quantité> <prix>");
                        }
                    }
                }

            }
            return false;
        }
        return false;
    }

    private void enchereManager(Player player){
        MaterialData item2 = new MaterialData(player.getItemInHand().getType(), (byte) 0);
        ItemMeta meta = player.getItemInHand().getItemMeta();
        ItemStack is = item2.toItemStack(Main.getInstance().quantité);
        is.setItemMeta(meta);
        //todo a retiré
        player.getInventory().addItem(is);
        //
        Main.getInstance().data.put(player, is);
        if(Main.getInstance().quantité == player.getItemInHand().getAmount()){
            ItemStack vide = new ItemStack(Material.AIR);
            player.setItemInHand(vide);
            Main.getInstance().enchereEnCours = 1;
        } else {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - Main.getInstance().quantité);
            Main.getInstance().enchereEnCours = 1;
        }
    }



}
