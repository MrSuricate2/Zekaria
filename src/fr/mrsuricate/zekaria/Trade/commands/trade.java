package fr.mrsuricate.zekaria.Trade.commands;

import fr.mrsuricate.zekaria.Main;
import fr.mrsuricate.zekaria.Trade.TradeHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class trade implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (cmd.getName().equalsIgnoreCase("trade")) {

            if (args.length == 0) {

                if (sender instanceof Player && sender.hasPermission("trade.use")) {
                    sender.sendMessage("§7/trade §c<joueur>");
                }
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("trade.reload")) {

                    Main.getInstance().reloadConfig();

                    sender.sendMessage("§aLa configuration a été rechargé.");
                }
            }

            if (args.length == 1)
            {
                if (sender instanceof Player && sender.hasPermission("trade.use") && !args[0].equalsIgnoreCase("reload")) {

                    Player player = (Player)sender;

                    String name = args[0];

                    Player onlinePlayer = Bukkit.getPlayer(name);

                    if (onlinePlayer != null) {

                        if (onlinePlayer.getUniqueId().toString().equals(player.getUniqueId().toString()))
                        {
                            player.sendMessage("§cTu ne peux pas échanger avec toi-même !");


                        }
                        else if (Main.getInstance().getTradeHandler().isWorld(player, onlinePlayer))
                        {
                            if (Main.getInstance().getTradeHandler().isRadius(player, onlinePlayer))
                            {
                                if (!Main.getInstance().getTradeHandler().isAlreadySent(player, onlinePlayer))
                                {
                                    boolean newRequest = Main.getInstance().getTradeHandler().updateRequest(player, onlinePlayer);

                                    if (newRequest)
                                    {
                                        player.sendMessage("§7La demande d'échange a été envoyée à §a"+onlinePlayer.getName()+"§7!");

                                        onlinePlayer.sendMessage("§7La demande d'échange a été envoyée de §a"+player.getName()+"§7!");
                                        onlinePlayer.sendMessage("§7Acceptez-le en utilisant §c/trade "+player.getName()+"§7!");
                                    }
                                    else
                                    {
                                        onlinePlayer.sendMessage("§7La demande d'échange' a été acceptée par §a"+player.getName()+"§7!");
                                        player.sendMessage("§7Vous avez accepté la demande d'échange de §a"+onlinePlayer.getName()+"§7!");


                                        Main.getInstance().getTradeHandler().removeRequests(player);
                                        Main.getInstance().getTradeHandler().removeRequests(onlinePlayer);
                                        Main.getInstance().trade.put(player, onlinePlayer);
                                        Main.getInstance().trade.put(onlinePlayer, player);
                                        Main.getInstance().getTradeHandler().open(player, onlinePlayer);
                                    }

                                }
                                else
                                {
                                    player.sendMessage("§7Une demande d'échange a déjà été envoyée à §a"+onlinePlayer.getName()+"§7!");
                                }

                            }
                            else
                            {
                                player.sendMessage("§7La demande d'échange a échoué, le joueur est trop loin§7!");
                            }

                        }
                        else
                        {
                            player.sendMessage("§7La demande d'échange ne peut pas être envoyé, le joueur est dans un autre mondes§7!");

                        }


                    }
                    else {

                        player.sendMessage("§cLe joueur demandé n’existe pas!");
                    }
                }
            }
        }





        return false;
    }
}
