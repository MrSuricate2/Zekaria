package fr.mrsuricate.zekaria.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Rules implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("rules")){

                p.sendMessage("");

                p.sendMessage("§4--- §bRéglement§4 ---");
                p.sendMessage("");

                p.sendMessage("§8[1] §bSur le tchat, restez toujours courtois et limitez vos majuscules.");
                p.sendMessage("§8[2] §bLes insultes sont sanctionnables.");
                p.sendMessage("§8[3] §bLe TP-KILL est interdit et sanctionnable avec preuves.");
                p.sendMessage("§8[4] §bTout skin inapproprié seras sanctionné.");
                p.sendMessage("§8[5] §bLe spam, le flood, la provocation sont interdits.");
                p.sendMessage("§8[6] §bLes débats concernant la politique, la religion et le racisme ne seront pas toléré.");
                p.sendMessage("§8[7] §bToutes usurpation d'identité sera sanctionné.");
                p.sendMessage("§8[8] §bLa publicité pour d'autres serveur ne sont pas toléré.");
                p.sendMessage("§8[9] §bSeul les mods optifine, damage indicator, HUD et Schematica sont autorisés.");
                p.sendMessage("§8[10] §bL'utilisation du market publique à des fins d'extension d'inventaire est interdite.");
                p.sendMessage("§8[11] §bTout actes commerciale entre joueurs métant en relation une monnaie non virtuelle (dollar, euro) est prohibé sur toute l'infrastructure Zekaria.");
                p.sendMessage("§8[12] §bL'utilisation de double compte est sanctionnable.");
                p.sendMessage("§8[13] §bLa trahison est autorisé.");
                p.sendMessage("§8[14] §bLes use-bug sont interdits.");
                p.sendMessage("§8[15] §bL'utilisation d'un VPN à des fin malveillante est strictement interdit.");
                p.sendMessage("§8[16] §bToute atteinte par déni de service conformément à l'article §4§n323-2 §bdu Code Pénal envers toute l'infrastructure de Zekaria.fr sera sanctionné.");

                p.sendMessage("");

                return true;
            }
        }

        return false;
    }
}
