package fr.mrsuricate.zekaria.enchere;

import fr.mrsuricate.zekaria.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.RegisteredServiceProvider;

public class enchere implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            if(cmd.getName().equalsIgnoreCase("enchere")){
                if(sender.hasPermission("enchere.use")){
                    setupEconomy();
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
                        }
                        if(Main.getInstance().enchereEnCours == 1){
                            if(args[0].equalsIgnoreCase("mise")){
                                if(args.length == 2){
                                    try{
                                        Main.getInstance().bidup = Integer.parseInt(args[1]);
                                    } catch (Exception e){
                                        sender.sendMessage("§cMauvais prix");
                                        Main.getInstance().bidup = 0;
                                    }
                                    if(economy.getBalance((OfflinePlayer) sender) >= Main.getInstance().bidup){
                                        if(Main.getInstance().bidup >= Main.getInstance().prixDeDepart){
                                            if(Main.getInstance().bid.isEmpty()){
                                                Main.getInstance().bid.put((Player) sender,Main.getInstance().bidup);
                                                Main.getInstance().lastbid = (Player) sender;
                                                economy.withdrawPlayer((OfflinePlayer) sender, Main.getInstance().bidup);
                                                sender.sendMessage("§aVotre Mise à bien était enregistré");
                                            } else {
                                                if (Main.getInstance().bidup > Main.getInstance().bid.get(Main.getInstance().lastbid)){
                                                    economy.depositPlayer((OfflinePlayer) Main.getInstance().lastbid, Main.getInstance().bid.get(Main.getInstance().lastbid));
                                                    Main.getInstance().bid.remove(Main.getInstance().lastbid);
                                                    Main.getInstance().bid.put((Player) sender, Main.getInstance().bidup);
                                                    economy.withdrawPlayer((OfflinePlayer) sender, Main.getInstance().bidup);
                                                    sender.sendMessage("§aVotre Mise à bien était enregistré");
                                                } else {
                                                    sender.sendMessage("§aVous n'avez pas surencherie assez. Veuillez mettre plus de "+ Main.getInstance().bid.get(Main.getInstance().lastbid));
                                                }
                                            }

                                        } else {
                                            sender.sendMessage("§aVous n'avez pas surencherie assez. Veuillez mettre plus ou égal a "+ Main.getInstance().prixDeDepart);
                                        }
                                    } else {
                                        sender.sendMessage("§aVous n'avez pas assez d'argent");
                                    }

                                }
                            }
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
            new Lancementenchere(player, is).runTaskTimer(Main.getInstance(),0L, 20L);
        } else {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - Main.getInstance().quantité);
            Main.getInstance().enchereEnCours = 1;
            new Lancementenchere(player, is).runTaskTimer(Main.getInstance(),0L, 20L);
        }
    }

    public static Economy economy = null;
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Main.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null)
            economy = economyProvider.getProvider();
        return (economy != null);
    }

}
