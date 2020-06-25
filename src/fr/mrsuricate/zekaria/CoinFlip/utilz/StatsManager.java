package fr.mrsuricate.zekaria.CoinFlip.utilz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.entity.Player;

public class StatsManager extends StatsEntry {
    private HashMap<Player, StatsEntry> stats = new HashMap<>();

    private StatsEntry entry;

    public boolean inEntry(Player p) {
        return this.stats.containsKey(p);
    }

    public void createEntry(Player p) {
        this.entry = this.stats.getOrDefault(p, new StatsManager());
        this.stats.put(p, this.entry);
    }


    public void incrementWin(Player p) {
        String name = p.getName();
        int win = (int) Main.getInstance().getcf().get(name+".win");
        win++;
        Main.getInstance().getcf().set(name+".win", win);
        try {
            Main.getInstance().getcf().save(Main.getInstance().cf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void incrementLose(Player p) {
        String name = p.getName();
        int lose = (int) Main.getInstance().getcf().get(name+".lose");
        lose++;
        Main.getInstance().getcf().set(name+".lose", lose);
        try {
            Main.getInstance().getcf().save(Main.getInstance().cf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getWinner(Player first, Player second) {
        if (Math.random() > 0.5D) {
            incrementWin(first);
            incrementLose(second);
            return first;
        }
        incrementWin(second);
        incrementLose(first);
        return second;
    }

    public static void toString(Player p) {
        p.sendMessage(Chat.color("&e&l&nStatistique"));
        p.sendMessage(Chat.color(""));
        p.sendMessage(Chat.color("&6&l* &eNombre de victoire: &f" +Main.getInstance().getcf().get(p.getName()+".win")));
        p.sendMessage(Chat.color("&6&l* &eNombre de défaite: &f" +Main.getInstance().getcf().get(p.getName()+".lose")));
        p.sendMessage(Chat.color(""));
    }
}
