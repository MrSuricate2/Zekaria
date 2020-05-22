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

                p.sendMessage("§8[1] §bSur le tchat, restez toujours §dcourtois §bet §nlimitez vos majuscules.");
                p.sendMessage("§8[2] §bLes §cinsultes §bsont sanctionnables.");
                p.sendMessage("§8[3] §bLe §dTP-KILL §best interdit et sanctionnable §navec preuves.");
                p.sendMessage("§8[4] §bTout §dskin inapproprié §bseras sanctionné.");
                p.sendMessage("§8[5] §bLe §dspam§b, le §dflood§b, la §dprovocation §bsont interdits.");
                p.sendMessage("§8[6] §bLes débats concernant la §dpolitique§b, la §dreligion §bet le §dracisme §bne seront pas toléré.");
                p.sendMessage("§8[7] §bToutes §dusurpation d'identité §bsera sanctionné.");
                p.sendMessage("§8[8] §bLa §dpublicité §bpour d'autres serveur ne sera pas toléré.");
                p.sendMessage("§8[9] §bSeul les mods §doptifine§b, §ddamage indicator§b, §dHUD §bet §dSchematica  §bsont autorisés.");
                p.sendMessage("§8[10] §bL'utilisation du §6market publique §bà des fins §cd'extension d'inventaire §best interdite.");
                p.sendMessage("§8[11] §bTout §cactes commerciale entre joueurs §bmétant en relation une §cmonnaie non virtuelle (dollar, euro) §best prohibé sur toute l'infrastructure Zekaria.");
                p.sendMessage("§8[12] §bL'utilisation de §ddouble compte §best sanctionnable.");
                p.sendMessage("§8[13] §bLa §dtrahison §best autorisé.");
                p.sendMessage("§8[14] §bLes §duse-bug §bsont interdits.");
                p.sendMessage("§8[15] §bL'utilisation d'un §dVPN §bà des fin §cmalveillante §best strictement interdit.");
                p.sendMessage("§8[16] §bToute atteinte par déni de service conformément à l'article §4§n323-2 §bdu Code Pénal envers toute l'infrastructure de Zekaria.fr sera sanctionné.");

                p.sendMessage("");

                return true;
            }
        }

        return false;
    }
}
