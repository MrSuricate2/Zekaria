package fr.mrsuricate.zekaria.CoinFlip.commands;

import fr.mrsuricate.zekaria.CoinFlip.utilz.Chat;
import fr.mrsuricate.zekaria.CoinFlip.utilz.CoinManager;
import fr.mrsuricate.zekaria.CoinFlip.utilz.InventoryManager;
import fr.mrsuricate.zekaria.Main;
import java.text.DecimalFormat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinFlipCommand implements CommandExecutor {
    private CoinManager coins = Main.getInstance().getCoins();

    private InventoryManager menu = Main.getInstance().getMenuManager();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("coinflip")) {
            Player p = (Player)sender;
            DecimalFormat df = new DecimalFormat("# ###");
            if (args.length == 0) {
                p.openInventory(Main.getInstance().getMenu());
                return false;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("toggle")) {
                    if (!Main.getInstance().getBroadcast().inEntry(p)) {
                        Main.getInstance().getBroadcast().createEntry(p);
                        p.sendMessage(Chat.color(Main.getInstance().getBroadcast().toString(p)));
                        return false;
                    }
                    Main.getInstance().getBroadcast().removeEntry(p);
                    p.sendMessage(Chat.color(Main.getInstance().getBroadcast().toString(p)));
                    return false;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    Main.getInstance().reloadConfig();
                    new InventoryManager();
                    return false;
                }
                if (args[0].equalsIgnoreCase("cancel")) {
                    if (this.coins.inEntry(p)) {
                        double amount = this.coins.getEntry().get(p).getAmount();
                        Main.getEconomy().depositPlayer(p.getName(), amount);
                        this.coins.removeEntry(p);
                        this.menu.updateInv();
                        p.sendMessage(Chat.color(Main.getInstance().getConfig().getString("Messages.ReceivedMoney").replaceAll("%amount%", df.format(amount))));
                        p.sendMessage(Chat.color(Main.getInstance().getConfig().getString("Messages.Canceled")));
                        return false;
                    }
                    p.sendMessage(Chat.color(Main.getInstance().getConfig().getString("Messages.NotInBet")));
                } else {
                    p.sendMessage(Chat.color(Main.getInstance().getConfig().getString("Messages.CanceledHelp")));
                    return false;
                }
            }
            if (args.length == 2)
                try {
                    double amount = Double.parseDouble(args[0]);
                    boolean side = this.coins.getBooleanConverted(args[1]);
                    if (this.coins.getEntry().size() < this.menu.getSize())
                        if (!this.coins.inEntry(p)) {
                            if (Main.getEconomy().getBalance(p) >= amount) {
                                if (amount >= Main.getInstance().getConfig().getInt("minAmount")) {
                                    Main.getEconomy().withdrawPlayer(p.getName(), amount);
                                    p.sendMessage(Chat.color(Main.getInstance().getConfig().getString("Messages.LostMoney").replaceAll("%amount%", df.format(amount))));
                                    p.sendMessage(Chat.color(Main.getInstance().getConfig().getString("Messages.Entered").replaceAll("%amount%", df.format(amount))));
                                    if (!Main.getInstance().getStats().inEntry(p))
                                        Main.getInstance().getStats().createEntry(p);
                                    this.coins.createEntry(p, amount, side);
                                    this.menu.updateInv();
                                    return false;
                                }
                                p.sendMessage(Chat.color(Main.getInstance().getConfig().getString("Messages.NotEnoughEnterMoney")));
                            } else {
                                p.sendMessage(Chat.color(Main.getInstance().getConfig().getString("Messages.NotEnoughMoney")));
                            }
                        } else {
                            p.sendMessage(Chat.color(Main.getInstance().getConfig().getString("Messages.AlreadyInBet")));
                        }
                } catch (NumberFormatException e) {
                    p.sendMessage(Chat.color(": &6&n/coinflip <montant> <face/pile>"));
                }
        }
        return false;
    }
}
