package fr.mrsuricate.zekaria.giveall;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import static org.bukkit.Bukkit.getServer;

public class Giveall implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (cmd.getName().equalsIgnoreCase("giveall")) {
            if (sender instanceof Player)
                if (!((Player)sender).hasPermission("giveall.use")) {
                    sender.sendMessage("§8[§4Give All§8] §6Vous n'avez pas la permission !");
                    return true;
                }
            if (args.length > 0) {
                String m = "";
                String d = "";
                boolean passedColon = false;
                byte b1;
                int i;
                char[] arrayOfChar;
                for (i = (arrayOfChar = args[0].toCharArray()).length, b1 = 0; b1 < i; ) {
                    char c = arrayOfChar[b1];
                    if (c == ':') {
                        passedColon = true;
                    } else if (!passedColon) {
                        m = String.valueOf(m) + c;
                    } else {
                        d = String.valueOf(d) + c;
                    }
                    b1++;
                }
                if (d.equals(""))
                    d = "0";
                Material mat = null;
                try {
                    mat = Material.getMaterial(Integer.parseInt(m));
                } catch (Exception e) {
                    mat = Material.getMaterial(m.toUpperCase());
                }
                if (mat == null) {
                    sender.sendMessage("§8[§4Give All§8] §6Ceci n'est pas un item !");
                    return true;
                }
                byte data = 0;
                try {
                    data = Byte.parseByte(d);
                } catch (Exception e) {
                    data = 0;
                }
                int amount = 1;
                if (args.length > 1)
                    try {
                        amount = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        amount = 1;
                    }
                if (amount > 64) {
                    amount = 64;
                } else if (amount < 1) {
                    sender.sendMessage("§8[§4Give All§8] §6Merci de préciser un chiffre au dessus de 1");
                    return true;
                }
                MaterialData md = new MaterialData(mat, data);
                md.setData(data);
                ItemStack is = md.toItemStack(amount);
                byte b2;
                int j;
                Player[] arrayOfPlayer;
                String item = md.toString();
                item = item.substring(0,(item.length() - 3));
                item = item.replace("(","");
                for (j = (arrayOfPlayer = getServer().getOnlinePlayers().toArray(new Player[0])).length, b2 = 0; b2 < j; ) {
                    Player p = arrayOfPlayer[b2];
                    p.getInventory().addItem(new ItemStack[] { is });

                    Bukkit.getConsoleSender().sendMessage(item);
                    //TODO
                    p.sendMessage("");
                    p.sendMessage("§8[§4Give All§8] §6Vous avez reçu " + amount + " " + item);
                    p.sendMessage("");
                    b2++;
                }
            }
        }

        return false;
    }
}
