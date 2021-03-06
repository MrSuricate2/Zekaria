package fr.mrsuricate.zekaria.Trade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.protobuf.StringValue;
import fr.mrsuricate.zekaria.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;



public class TradeInventory
{
    private Player sender;
    private Player receiver;
    private Inventory senderInventory;
    private Inventory receiverInventory;
    private List<Integer> youSlots;
    private List<Integer> otherSlots;
    private boolean finished = false;
    private boolean closed = false;
    private int singleClose;
    private List<String> closedPlayers = new ArrayList<>();




    public TradeInventory(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;

        this.senderInventory = Bukkit.createInventory(sender, 54, "Vous                  Lui");
        this.receiverInventory = Bukkit.createInventory(receiver, 54, "Vous                  Lui");

        List<Integer> wallSlots = Arrays.asList(new Integer[] { Integer.valueOf(4), Integer.valueOf(13), Integer.valueOf(22), Integer.valueOf(31), Integer.valueOf(40), Integer.valueOf(49) });
        List<Integer> statusSlots = Arrays.asList(new Integer[] { Integer.valueOf(48), Integer.valueOf(50) });
        int acceptedSlot = 45;
        int declinedSlot = 46;

        int solde = 47;

        this.youSlots = Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(18), Integer.valueOf(19), Integer.valueOf(20), Integer.valueOf(21) });
        this.otherSlots = Arrays.asList(new Integer[] { Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(17), Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(25), Integer.valueOf(26) });

        for (Integer wallSlot : wallSlots) {
            this.senderInventory.setItem(wallSlot.intValue(), ItemStackUtils.getItem(Material.STAINED_GLASS_PANE, 1, 0, " ", new String[0]));
            this.receiverInventory.setItem(wallSlot.intValue(), ItemStackUtils.getItem(Material.STAINED_GLASS_PANE, 1, 0, " ", new String[0]));
        }



        this.senderInventory.setItem(27,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§a§l+ 10 000 €", new String[0]));
        this.senderInventory.setItem(28,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§a§l+ 1 000 €", new String[0]));
        this.senderInventory.setItem(29,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§a§l+ 100 €", new String[0]));
        this.senderInventory.setItem(30,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§a§l+ 10 €", new String[0]));
        this.senderInventory.setItem(36,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§c§l- 10 000 €", new String[0]));
        this.senderInventory.setItem(37,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§c§l- 1 000 €", new String[0]));
        this.senderInventory.setItem(38,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§c§l- 100 €", new String[0]));
        this.senderInventory.setItem(39,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§c§l- 10 €", new String[0]));

        this.receiverInventory.setItem(27,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§a§l+ 10 000 €", new String[0]));
        this.receiverInventory.setItem(28,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§a§l+ 1 000 €", new String[0]));
        this.receiverInventory.setItem(29,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§a§l+ 100 €", new String[0]));
        this.receiverInventory.setItem(30,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§a§l+ 10 €", new String[0]));
        this.receiverInventory.setItem(36,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§c§l- 10 000 €", new String[0]));
        this.receiverInventory.setItem(37,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§c§l- 1 000 €", new String[0]));
        this.receiverInventory.setItem(38,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§c§l- 100 €", new String[0]));
        this.receiverInventory.setItem(39,ItemStackUtils.getItem(Material.PAPER, 1, 0, "§c§l- 10 €", new String[0]));


        for (Integer statusSlot : statusSlots) {
            this.senderInventory.setItem(statusSlot.intValue(), ItemStackUtils.getItem(Material.INK_SACK, 1, 9, "§7Statut en attente...", new String[0]));
            this.receiverInventory.setItem(statusSlot.intValue(), ItemStackUtils.getItem(Material.INK_SACK, 1, 9, "§7Statut en attente...", new String[0]));
        }

        this.senderInventory.setItem(acceptedSlot, ItemStackUtils.getItem(Material.WOOL, 1, 5, "§aCliquez ici pour accepter l'échange!", new String[0]));
        this.senderInventory.setItem(declinedSlot, ItemStackUtils.getItem(Material.WOOL, 1, 14, "§cCliquez ici pour refuser l'échange'!", new String[0]));

        this.receiverInventory.setItem(acceptedSlot, ItemStackUtils.getItem(Material.WOOL, 1, 5, "§aCliquez ici pour accepter l'échange!", new String[0]));
        this.receiverInventory.setItem(declinedSlot, ItemStackUtils.getItem(Material.WOOL, 1, 14, "§cCliquez ici pour refuser l'échange!", new String[0]));

        sender.openInventory(this.senderInventory);
        receiver.openInventory(this.receiverInventory);
    }


    public boolean isFinished() {
        return this.finished;
    }


    public void setFinished() {
        this.finished = true;

        List<Integer> statusSlots = Arrays.asList(new Integer[] { Integer.valueOf(48), Integer.valueOf(50) });

        for (Integer statusSlot : statusSlots) {
            this.senderInventory.setItem(statusSlot.intValue(), ItemStackUtils.getItem(Material.INK_SACK, 1, 0, "§b > Échangé <", new String[0]));
            this.receiverInventory.setItem(statusSlot.intValue(), ItemStackUtils.getItem(Material.INK_SACK, 1, 0, "§b > Échangé <", new String[0]));
        }

        Player player = getSender();
        Player p = Main.getInstance().trade.get(player);
        economy.withdrawPlayer(player, Main.getInstance().setmoney);
        economy.depositPlayer(player, Main.getInstance().setmoney2);
        economy.withdrawPlayer(p, Main.getInstance().setmoney2);
        economy.depositPlayer(p, Main.getInstance().setmoney);
        Main.getInstance().setmoney = 0;
        Main.getInstance().setmoney2 = 0;
        Main.getInstance().trade.remove(player);
        Main.getInstance().trade.remove(p);
        getSender().closeInventory();
        getReceiver().closeInventory();


    }


    public List<Integer> getYouSlots() {
        return this.youSlots;
    }

    public List<Integer> getOtherSlots() {
        return this.otherSlots;
    }

    public Player getSender() {
        return this.sender;
    }

    public Player getReceiver() {
        return this.receiver;
    }

    public Inventory getSenderInventory() {
        return this.senderInventory;
    }

    public Inventory getReceiverInventory() {
        return this.receiverInventory;
    }


    public List<ItemStack> getYouItems(Player player) {
        List<ItemStack> itemStackList = new ArrayList<ItemStack>();

        if (player.getUniqueId().toString().equals(getSender().getUniqueId().toString()))
        {
            for (Iterator<Integer> iterator = getYouSlots().iterator(); iterator.hasNext(); ) { int youSlot = ((Integer)iterator.next()).intValue();

                itemStackList.add(getSenderInventory().getItem(youSlot)); }

        }



        if (player.getUniqueId().toString().equals(getReceiver().getUniqueId().toString()))
        {
            for (Iterator<Integer> iterator = getYouSlots().iterator(); iterator.hasNext(); ) { int youSlot = ((Integer)iterator.next()).intValue();

                itemStackList.add(getReceiverInventory().getItem(youSlot)); }

        }



        return itemStackList;
    }

    public List<ItemStack> getOtherItems(Player player) {
        List<ItemStack> itemStackList = new ArrayList<ItemStack>();

        if (player.getUniqueId().toString().equals(getSender().getUniqueId().toString()))
        {
            for (Iterator<Integer> iterator = getOtherSlots().iterator(); iterator.hasNext(); ) { int otherSlot = ((Integer)iterator.next()).intValue();

                itemStackList.add(getSenderInventory().getItem(otherSlot)); }

        }



        if (player.getUniqueId().toString().equals(getReceiver().getUniqueId().toString()))
        {
            for (Iterator<Integer> iterator = getOtherSlots().iterator(); iterator.hasNext(); ) { int otherSlot = ((Integer)iterator.next()).intValue();

                itemStackList.add(getReceiverInventory().getItem(otherSlot)); }

        }



        return itemStackList;
    }

    public void giveItemsBack() {
        List<ItemStack> senderItems = getYouItems(getSender());
        List<ItemStack> receiverItems = getYouItems(getReceiver());

        List<ItemStack> senderDropItems = new ArrayList<ItemStack>();
        List<ItemStack> receiverDropItems = new ArrayList<ItemStack>();

        for (ItemStack senderItem : senderItems) {
            if (senderItem != null) {
                for (ItemStack itemStack : getSender().getInventory().addItem(new ItemStack[] { senderItem }).values()) {
                    senderDropItems.add(itemStack);
                }
            }
        }

        for (ItemStack receiverItem : receiverItems) {
            if (receiverItem != null) {
                for (ItemStack itemStack : getReceiver().getInventory().addItem(new ItemStack[] { receiverItem }).values()) {
                    receiverDropItems.add(itemStack);
                }
            }
        }

        for (ItemStack dropSenderItem : senderDropItems) {
            if (dropSenderItem != null) {
                getSender().getLocation().getWorld().dropItem(getSender().getLocation(), dropSenderItem);
            }
        }

        for (ItemStack dropReceiverItem : receiverDropItems) {
            if (dropReceiverItem != null) {
                getReceiver().getLocation().getWorld().dropItem(getReceiver().getLocation(), dropReceiverItem);
            }
        }
    }

    public boolean isClosed(Player player) {
        if (this.closedPlayers.contains(player.getUniqueId().toString())) {
            return true;
        }

        return false;
    }

    public void closeInventory(Player player) {
        if (!this.closedPlayers.contains(player.getUniqueId().toString())) {
            this.closedPlayers.add(player.getUniqueId().toString());
        }

        if (isFinished()) {

            if (getSender().getUniqueId().toString().equals(player.getUniqueId().toString())) {

                List<ItemStack> senderItems = getOtherItems(getSender());

                List<ItemStack> senderDropItems = new ArrayList<ItemStack>();

                for (ItemStack senderItem : senderItems) {
                    if (senderItem != null) {
                        for (ItemStack itemStack : getSender().getInventory().addItem(new ItemStack[] { senderItem }).values()) {
                            if (itemStack != null) {
                                senderDropItems.add(itemStack);
                            }
                        }
                    }
                }

                for (ItemStack dropSenderItem : senderDropItems) {
                    if (dropSenderItem != null) {
                        getSender().getLocation().getWorld().dropItem(getSender().getLocation(), dropSenderItem);
                    }
                }
            }


            if (getReceiver().getUniqueId().toString().equals(player.getUniqueId().toString())) {

                List<ItemStack> receiverItems = getOtherItems(getReceiver());

                List<ItemStack> receiverDropItems = new ArrayList<ItemStack>();

                for (ItemStack receiverItem : receiverItems) {
                    if (receiverItem != null) {
                        for (ItemStack itemStack : getReceiver().getInventory().addItem(new ItemStack[] { receiverItem }).values()) {
                            if (itemStack != null) {
                                receiverDropItems.add(itemStack);
                            }
                        }
                    }
                }

                for (ItemStack dropReceiverItem : receiverDropItems) {
                    if (dropReceiverItem != null) {
                        getReceiver().getLocation().getWorld().dropItem(getReceiver().getLocation(), dropReceiverItem);
                    }
                }
            }
        }
    }

    public void closeInventories(final Player player) {
        if (this.closed) {
            return;
        }

        (new BukkitRunnable()
        {
            public void run() {
                if (TradeInventory.this.getSender().getUniqueId().toString().equals(player.getUniqueId().toString()))
                {
                    InventoryView view = TradeInventory.this.getReceiver().getOpenInventory();

                    if (view != null) {
                        view.close();
                    }
                }
                else
                {
                    InventoryView view = TradeInventory.this.getSender().getOpenInventory();

                    if (view != null) {
                        view.close();
                    }
                }

            }
        }).runTask((Plugin)Main.getInstance());

        getSender().sendMessage("§cL'échange a été annulé!");
        getReceiver().sendMessage("§cL'échange a été annulé!");


        Player p = Main.getInstance().trade.get(player);
        Main.getInstance().trade.remove(player);
        Main.getInstance().trade.remove(p);

        this.closed = true;

        giveItemsBack();
    }

    public void addSingleClose() {
        this.singleClose++;
    }

    public boolean isFullyClosed() {
        if (this.singleClose >= 2) {
            return true;
        }
        return false;
    }

    public Inventory getInventory(Inventory clickedInventory) {
        if (clickedInventory.equals(getSenderInventory())) {
            return getReceiverInventory();
        }
        if (clickedInventory.equals(getReceiverInventory())) {
            return getSenderInventory();
        }
        return null;
    }


    public boolean isInventory(Inventory inventory) {
        if (inventory.equals(getSenderInventory()) || inventory.equals(getReceiverInventory()))
        {
            return true;
        }


        return false;
    }


    public void updateSlots(Inventory clickedInventory, Inventory inventory) {
        this.youSlots = Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(18), Integer.valueOf(19), Integer.valueOf(20), Integer.valueOf(21) });
        this.otherSlots = Arrays.asList(new Integer[] { Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(17), Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(25), Integer.valueOf(26) });

        Map<Integer, ItemStack> youItems = new HashMap<Integer, ItemStack>();

        for (Integer youId : this.youSlots) {

            ItemStack itemStack = clickedInventory.getItem(youId.intValue());

            if (itemStack != null &&
                    itemStack.getType() != Material.AIR) {
                youItems.put(youId, itemStack);
            }
        }



        for (Integer otherId : this.otherSlots)
        {
            inventory.clear(otherId.intValue());
        }


        for (Integer youId : this.youSlots) {

            if (youItems.containsKey(youId))
            {
                inventory.setItem(youId.intValue() + 5, youItems.get(youId));
            }
        }
    }





    public void setItem(int slot, Player player) {
        if (isFinished()) {
            return;
        }
        setupEconomy();

        if (player.getUniqueId().toString().equals(getSender().getUniqueId().toString())) {

            if (slot == 45) {

                getSenderInventory().setItem(48, ItemStackUtils.getItem(Material.INK_SACK, 1, 10, "§aAccepté!", new String[0]));
                getReceiverInventory().setItem(50, ItemStackUtils.getItem(Material.INK_SACK, 1, 10, "§aAccepté!", new String[0]));

                if (getSenderInventory().getItem(48).equals(ItemStackUtils.getItem(Material.INK_SACK, 1, 10, "§aAccepté!", new String[0])) &&
                        getSenderInventory().getItem(50).equals(ItemStackUtils.getItem(Material.INK_SACK, 1, 10, "§aAccepté!", new String[0]))) {
                    setFinished();
                }
            }

            if (slot == 46) {

                getSenderInventory().setItem(48, ItemStackUtils.getItem(Material.INK_SACK, 1, 1, "§cRefusé!", new String[0]));
                getReceiverInventory().setItem(50, ItemStackUtils.getItem(Material.INK_SACK, 1, 1, "§cRefusé!", new String[0]));

                if (getSenderInventory().getItem(48).equals(ItemStackUtils.getItem(Material.INK_SACK, 1, 1, "§cRefusé!", new String[0]))){
                    getSender().closeInventory();
                    getReceiver().closeInventory();
                }
                if (getSenderInventory().getItem(50).equals(ItemStackUtils.getItem(Material.INK_SACK, 1, 1, "§cRefusé!", new String[0]))){
                    getSender().closeInventory();
                    getReceiver().closeInventory();
                }
            }

            double balance = economy.getBalance(getSender());
            if (slot == 27){
                if (balance >= 10000D){
                    Main.getInstance().setmoney = Main.getInstance().setmoney + 10000;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                }
            }
            if (slot == 28){
                if (balance >= 1000D){
                    Main.getInstance().setmoney = Main.getInstance().setmoney + 1000;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                }
            }
            if (slot == 29){
                if (balance >= 100D){
                    Main.getInstance().setmoney = Main.getInstance().setmoney + 100;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                }
            }
            if (slot == 30){
                if (balance >= 10D){
                    Main.getInstance().setmoney = Main.getInstance().setmoney + 10;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                }
            }
            if (slot == 36){
                if (Main.getInstance().setmoney >= 10000D){
                    Main.getInstance().setmoney = Main.getInstance().setmoney - 10000;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                } else {
                    Main.getInstance().setmoney = 0;
                }
            }
            if (slot == 37){
                if (Main.getInstance().setmoney >= 1000D){
                    Main.getInstance().setmoney = Main.getInstance().setmoney - 1000;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                } else {
                    Main.getInstance().setmoney = 0;
                }
            }
            if (slot == 38){
                if (Main.getInstance().setmoney >= 100D){
                    Main.getInstance().setmoney = Main.getInstance().setmoney - 100;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                } else {
                    Main.getInstance().setmoney = 0;
                }
            }
            if (slot == 39){
                if (Main.getInstance().setmoney >= 10D){
                    Main.getInstance().setmoney = Main.getInstance().setmoney - 10;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                } else {
                    Main.getInstance().setmoney = 0;
                }
            }
        }




        if (player.getUniqueId().toString().equals(getReceiver().getUniqueId().toString())) {

            if (slot == 45) {

                getSenderInventory().setItem(50, ItemStackUtils.getItem(Material.INK_SACK, 1, 10, "§aAccepté!", new String[0]));
                getReceiverInventory().setItem(48, ItemStackUtils.getItem(Material.INK_SACK, 1, 10, "§aAccepté!", new String[0]));

                if (getReceiverInventory().getItem(50).equals(ItemStackUtils.getItem(Material.INK_SACK, 1, 10, "§aAccepté!", new String[0])) &&
                        getReceiverInventory().getItem(48).equals(ItemStackUtils.getItem(Material.INK_SACK, 1, 10, "§aAccepté!", new String[0]))) {
                    setFinished();
                }
            }

            if (slot == 46) {

                getSenderInventory().setItem(50, ItemStackUtils.getItem(Material.INK_SACK, 1, 1, "§cRefusé!", new String[0]));
                getReceiverInventory().setItem(48, ItemStackUtils.getItem(Material.INK_SACK, 1, 1, "§cRefusé!", new String[0]));

                if (getSenderInventory().getItem(48).equals(ItemStackUtils.getItem(Material.INK_SACK, 1, 1, "§cRefusé!", new String[0]))){
                    getSender().closeInventory();
                    getReceiver().closeInventory();
                }
                if (getSenderInventory().getItem(50).equals(ItemStackUtils.getItem(Material.INK_SACK, 1, 1, "§cRefusé!", new String[0]))){
                    getSender().closeInventory();
                    getReceiver().closeInventory();
                }

            }
            double balance = economy.getBalance(player);
            if (slot == 27){
                if (balance >= 10000D){
                    Main.getInstance().setmoney2 = Main.getInstance().setmoney2 + 10000;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                }
            }
            if (slot == 28){
                if (balance >= 1000D){
                    Main.getInstance().setmoney2 = Main.getInstance().setmoney2 + 1000;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                }
            }
            if (slot == 29){
                if (balance >= 100D){
                    Main.getInstance().setmoney2 = Main.getInstance().setmoney2 + 100;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                }
            }
            if (slot == 30){
                if (balance >= 10D){
                    Main.getInstance().setmoney2 = Main.getInstance().setmoney2 + 10;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                }
            }
            if (slot == 36){
                if (Main.getInstance().setmoney2 >= 10000D){
                    Main.getInstance().setmoney2 = Main.getInstance().setmoney2 - 10000;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                } else {
                    Main.getInstance().setmoney2 = 0;
                }
            }
            if (slot == 37){
                if (Main.getInstance().setmoney2 >= 1000D){
                    Main.getInstance().setmoney2 = Main.getInstance().setmoney2 - 1000;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                } else {
                    Main.getInstance().setmoney2 = 0;
                }
            }
            if (slot == 38){
                if (Main.getInstance().setmoney2 >= 100D){
                    Main.getInstance().setmoney2 = Main.getInstance().setmoney2 - 100;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                } else {
                    Main.getInstance().setmoney2 = 0;
                }
            }
            if (slot == 39){
                if (Main.getInstance().setmoney2 >= 10D){
                    Main.getInstance().setmoney2 = Main.getInstance().setmoney2 - 10;
                    getReceiverInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney2+" §7$","§7Il donne §a"+Main.getInstance().setmoney+" §7$"));
                    getSenderInventory().setItem(47, ItemStackUtils.getItem(Material.GOLD_INGOT, 1, 0, "§eArgent", "§7Vous donnez §a"+Main.getInstance().setmoney+" §7$","§7Il donne §a"+Main.getInstance().setmoney2+" §7$" ));
                } else {
                    Main.getInstance().setmoney2 = 0;
                }
            }
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
