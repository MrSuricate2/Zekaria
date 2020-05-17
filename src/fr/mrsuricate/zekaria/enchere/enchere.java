package fr.mrsuricate.zekaria.enchere;

import fr.mrsuricate.zekaria.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Collection;
import java.util.Iterator;

public class enchere implements CommandExecutor {

    public String convertItemStackToJsonRegular(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound compound = new NBTTagCompound();
        compound = nmsItemStack.save(compound);

        return compound.toString();
    }

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
                                                    Main.getInstance().item2 = new MaterialData(((Player) sender).getItemInHand().getType(), (byte) 0);
                                                    Main.getInstance().dura = ((Player) sender).getItemInHand().getDurability();
                                                    Main.getInstance().meta = ((Player) sender).getItemInHand().getItemMeta();
                                                    Main.getInstance().is = Main.getInstance().item2.toItemStack(Main.getInstance().quantité);
                                                    Main.getInstance().is.setItemMeta(Main.getInstance().meta);
                                                    Main.getInstance().is.setDurability(Main.getInstance().dura);
                                                    Main.getInstance().itemJson = convertItemStackToJsonRegular(Main.getInstance().is);

                                                    Main.getInstance().mat = ((Player) sender).getItemInHand().getType();

                                                    TextComponent message = new TextComponent(ChatColor.GREEN + sender.getName() + ChatColor.AQUA + " vient de mettre au enchere cette item : "+ ChatColor.RED + Main.getInstance().quantité +" "+ ChatColor.RED + Main.getInstance().mat + ChatColor.AQUA + " pour " + ChatColor.RED + Main.getInstance().prixDeDepart + ChatColor.AQUA + " $." );
                                                    message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(Main.getInstance().itemJson).create()));
                                                    message.setClickEvent( new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/enchere mise "));

                                                    Collection onlineplayer = Bukkit.getOnlinePlayers();
                                                    Iterator<Player> itstring = onlineplayer.iterator();
                                                    Bukkit.broadcastMessage("");
                                                    while(itstring.hasNext()){
                                                        Player value = itstring.next();
                                                        value.spigot().sendMessage(message);
                                                    }
                                                    Bukkit.broadcastMessage("");
                                                    Main.getInstance().namecreate = (Player) sender;
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
                                            if(Main.getInstance().namecreate.equals((Player) sender)){
                                                sender.sendMessage("§cVous ne pouvez pas surenchérir à votre propre enchere !");
                                            } else {
                                                if(Main.getInstance().bid.isEmpty()){
                                                    Main.getInstance().bid.put((Player) sender,Main.getInstance().bidup);
                                                    Main.getInstance().lastbid = (Player) sender;
                                                    economy.withdrawPlayer((OfflinePlayer) sender, Main.getInstance().bidup);
                                                    sender.sendMessage("§aVotre Mise à bien était enregistré");

                                                    TextComponent message2 = new TextComponent(ChatColor.GREEN + sender.getName()+ ChatColor.AQUA+" vient de surenchérir pour un total de : "+ChatColor.RED+ Main.getInstance().bidup + ChatColor.AQUA + " $ §bsur l'enchere : "+ChatColor.RED+ Main.getInstance().quantité+ " "+ChatColor.RED+ Main.getInstance().mat );
                                                    message2.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(Main.getInstance().itemJson).create()));
                                                    message2.setClickEvent( new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/enchere mise "));

                                                    Collection onlineplayer2 = Bukkit.getOnlinePlayers();
                                                    Iterator<Player> itstring2 = onlineplayer2.iterator();
                                                    Bukkit.broadcastMessage("");
                                                    while(itstring2.hasNext()){
                                                        Player value2 = itstring2.next();
                                                        value2.spigot().sendMessage(message2);
                                                    }
                                                    Bukkit.broadcastMessage("");
                                                } else {
                                                    if (Main.getInstance().bidup > Main.getInstance().bid.get(Main.getInstance().lastbid)){
                                                        economy.depositPlayer(Main.getInstance().lastbid, Main.getInstance().bid.get(Main.getInstance().lastbid));
                                                        Main.getInstance().bid.remove(Main.getInstance().lastbid);
                                                        Main.getInstance().bid.put((Player) sender, Main.getInstance().bidup);
                                                        Main.getInstance().lastbid = (Player) sender;
                                                        economy.withdrawPlayer((OfflinePlayer) sender, Main.getInstance().bidup);
                                                        sender.sendMessage("§aVotre Mise à bien était enregistré");

                                                        TextComponent message2 = new TextComponent(ChatColor.GREEN + sender.getName()+ ChatColor.AQUA+" vient de surenchérir pour un total de : "+ChatColor.RED+ Main.getInstance().bidup + ChatColor.GREEN +" $ sur l'enchere : "+ChatColor.AQUA+ Main.getInstance().quantité+ " "+ Main.getInstance().mat );
                                                        message2.setColor( ChatColor.AQUA );
                                                        message2.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(Main.getInstance().itemJson).create()));
                                                        message2.setClickEvent( new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/enchere mise "));

                                                        Collection onlineplayer2 = Bukkit.getOnlinePlayers();
                                                        Iterator<Player> itstring2 = onlineplayer2.iterator();
                                                        Bukkit.broadcastMessage("");
                                                        while(itstring2.hasNext()){
                                                            Player value2 = itstring2.next();
                                                            value2.spigot().sendMessage(message2);
                                                        }
                                                        Bukkit.broadcastMessage("");
                                                    } else {
                                                        sender.sendMessage("§aVous n'avez pas surencherie assez. Veuillez mettre plus de "+ Main.getInstance().bid.get(Main.getInstance().lastbid));
                                                    }
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
        short dura = player.getItemInHand().getDurability();
        ItemMeta meta = player.getItemInHand().getItemMeta();
        ItemStack is = item2.toItemStack(Main.getInstance().quantité);
        is.setItemMeta(meta);
        is.setDurability(dura);
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
