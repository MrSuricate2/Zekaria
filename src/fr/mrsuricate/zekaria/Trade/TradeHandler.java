package fr.mrsuricate.zekaria.Trade;

import java.util.*;
import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TradeHandler implements Listener {
    private Map<String, Map<String, Long>> requests = new HashMap<String, Map<String, Long>>();
    private List<TradeInventory> tradeInventories = new ArrayList<TradeInventory>();

    public TradeHandler() {
        (new BukkitRunnable()
        {
            public void run() {
                for (Map.Entry<String, Map<String, Long>> entries : TradeHandler.this.requests.entrySet())
                {
                    String playerUUID = entries.getKey();

                    Player bukkitPlayer = Bukkit.getPlayer(UUID.fromString(playerUUID));

                    Map<String, Long> players = entries.getValue();

                    for (String playersUUID : players.keySet()) {

                        if (System.currentTimeMillis() >= players.get(playersUUID).longValue()) {

                            players.remove(playersUUID);

                            Player otherBukkitPlayer = Bukkit.getPlayer(UUID.fromString(playersUUID));

                            if (bukkitPlayer != null && otherBukkitPlayer != null) {

                                bukkitPlayer.sendMessage("§7Demande d'échange avec §c"+otherBukkitPlayer.getName()+" §7a été annulé§7!");
                                otherBukkitPlayer.sendMessage("§7Demande d'échange avec §c"+bukkitPlayer.getName()+" §7a été annulé§7!");
                            }
                        }
                    }



                    TradeHandler.this.requests.put(playerUUID, players);

                }

            }
        }).runTaskTimer(Main.getInstance(), 5L, 5L);
    }



    public boolean updateRequest(Player sender, Player receiver) {
        if (isWorld(sender, receiver))
        {
            if (isRadius(sender, receiver)) {

                if (this.requests.containsKey(receiver.getUniqueId().toString())) {

                    Set<String> requestsReceiver = ((Map)this.requests.get(receiver.getUniqueId().toString())).keySet();

                    if (requestsReceiver.contains(sender.getUniqueId().toString()))
                    {




                        return false;
                    }
                }



                if (this.requests.containsKey(sender.getUniqueId().toString())) {

                    Map<String, Long> requestsSender = this.requests.get(sender.getUniqueId().toString());

                    if (!requestsSender.containsKey(receiver.getUniqueId().toString()))
                    {
                        requestsSender.put(receiver.getUniqueId().toString(), Long.valueOf(System.currentTimeMillis() + Integer.valueOf(20000)).longValue());
                        this.requests.put(sender.getUniqueId().toString(), requestsSender);





                        return true;
                    }

                }
                else {

                    Map<String, Long> emptyRequests = new HashMap<String, Long>();

                    emptyRequests.put(receiver.getUniqueId().toString(), Long.valueOf(System.currentTimeMillis() + Integer.valueOf(20000)).longValue());

                    this.requests.put(sender.getUniqueId().toString(), emptyRequests);






                    return true;
                }
            }
        }




        return true;
    }



    public void removeRequests(Player player) {
        if (this.requests.containsKey(player.getUniqueId().toString()))
        {
            this.requests.remove(player.getUniqueId().toString());
        }
    }




    public boolean isAlreadySent(Player sender, Player receiver) {
        if (this.requests.containsKey(sender.getUniqueId().toString()))
        {
            if (((Map)this.requests.get(sender.getUniqueId().toString())).containsKey(receiver.getUniqueId().toString()))
            {
                return true;
            }
        }



        return false;
    }



    public boolean isRadius(Player sender, Player receiver) {
        int radius = Main.getInstance().getConfig().getInt("Allow-Distance.radius");
        if(Main.getInstance().getConfig().getString("Allow-Distance.enable").equalsIgnoreCase("true")){
            if (sender.getWorld().getName().equals(receiver.getWorld().getName()))
            {
                if (sender.getLocation().distance(receiver.getLocation()) <= radius)
                {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }




    public boolean isWorld(Player sender, Player receiver) {
        if(Main.getInstance().getConfig().getString("Allow-inter-world.enable").equalsIgnoreCase("true")){
            if (sender.getWorld().getName().equals(receiver.getWorld().getName())) {
                return true;
            }
        } else {
            return true;
        }

        return false;
    }



    public void open(Player sender, Player receiver) {
        TradeInventory tradeInventory = new TradeInventory(sender, receiver);

        this.tradeInventories.add(tradeInventory);
    }

//todo fermer le trade si combats

    @EventHandler
    public void onGetDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            if(Main.getInstance().trade.containsKey(player)){
                player.closeInventory();
            }
        }
    }


    @EventHandler
    public void closeInventory(InventoryCloseEvent e) {
        Player player = (Player)e.getPlayer();

        TradeInventory tradeInventory = null;

        for (TradeInventory inventory : this.tradeInventories) {

            if (inventory.getSender().getUniqueId().toString().equals(player.getUniqueId().toString()) || inventory.getReceiver().getUniqueId().toString().equals(player.getUniqueId().toString()))
            {
                tradeInventory = inventory;
            }
        }



        if (tradeInventory != null)
        {
            if (tradeInventory.isFinished()) {

                if (!tradeInventory.isClosed(player))
                {
                    tradeInventory.closeInventory(player);

                    tradeInventory.addSingleClose();

                    if (tradeInventory.isFullyClosed()) {
                        this.tradeInventories.remove(tradeInventory);
                    }
                }

            }
            else {

                tradeInventory.closeInventories(player);


                this.tradeInventories.remove(tradeInventory);
            }
        }
    }





    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void Inventory(InventoryMoveItemEvent e) {
        Inventory clickedInventory = e.getDestination();
        TradeInventory tradeInventory = null;

        if (clickedInventory != null)
        {
            for (TradeInventory inventory : this.tradeInventories) {

                if (inventory.isInventory(clickedInventory)) {
                    tradeInventory = inventory;
                }
            }
        }



        if (tradeInventory != null)
        {
            e.setCancelled(true);
        }
    }




    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void dragInventory(InventoryDragEvent e) {
        if (e.getWhoClicked() instanceof Player) {

            final Inventory clickedInventory = e.getInventory();
            TradeInventory tradeInventory = null;

            if (clickedInventory != null) {

                for (TradeInventory inventory : this.tradeInventories) {

                    if (inventory.isInventory(clickedInventory)) {
                        tradeInventory = inventory;
                    }
                }


                if (tradeInventory != null) {

                    Set<Integer> slots = e.getRawSlots();

                    boolean disallowed = false;

                    if (tradeInventory.isFinished()) {

                        for (Integer slotId : slots)
                        {
                            if (tradeInventory.getYouSlots().contains(slotId))
                            {
                                disallowed = true;

                            }
                        }

                    }
                    else {

                        for (Integer slotId : slots) {

                            if (tradeInventory.getOtherSlots().contains(slotId))
                            {
                                disallowed = true;
                            }
                        }
                    }




                    if (disallowed) {
                        e.setCancelled(true);
                        e.setResult(Event.Result.DENY);

                        return;
                    }
                    int playerMinId = 54;
                    int playerMaxId = 89;

                    boolean playerInventory = false;
                    boolean foundNonItem = false;


                    if (playerInventory) {
                        return;
                    }

                    e.setCancelled(true);
                    e.setResult(Event.Result.DENY);

                    for (Integer slotId : slots) {

                        if (tradeInventory.isFinished()) {


                            if (tradeInventory.getOtherSlots().contains(slotId))
                            {
                                if (clickedInventory.getItem(slotId.intValue()) != null)
                                {
                                    if (clickedInventory.getItem(slotId.intValue()).getType() != Material.AIR) {

                                        e.setCancelled(false);
                                        e.setResult(Event.Result.ALLOW);

                                        (new BukkitRunnable()
                                        {
                                            public void run()
                                            {
                                                TradeInventory tradeInventory = null;

                                                for (TradeInventory inventory : TradeHandler.this.tradeInventories) {

                                                    if (inventory.isInventory(clickedInventory)) {
                                                        tradeInventory = inventory;
                                                    }
                                                }


                                                if (tradeInventory != null) {
                                                    tradeInventory.updateSlots(clickedInventory, tradeInventory.getInventory(clickedInventory));


                                                }
                                            }
                                        }).runTaskLater((Plugin)Main.getInstance(), 10L);
                                    }
                                }
                            }



                            continue;
                        }


                        if (tradeInventory.getYouSlots().contains(slotId)) {

                            e.setCancelled(false);
                            e.setResult(Event.Result.ALLOW);

                            (new BukkitRunnable()
                            {
                                public void run()
                                {
                                    TradeInventory tradeInventory = null;

                                    for (TradeInventory inventory : TradeHandler.this.tradeInventories) {

                                        if (inventory.isInventory(clickedInventory)) {
                                            tradeInventory = inventory;
                                        }
                                    }


                                    if (tradeInventory != null) {
                                        tradeInventory.updateSlots(clickedInventory, tradeInventory.getInventory(clickedInventory));

                                    }
                                }
                            }).runTaskLater((Plugin)Main.getInstance(), 10L);
                        }
                    }
                }
            }
        }
    }










    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void clickInventory(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {

            Player player = (Player)e.getWhoClicked();

            final Inventory clickedInventory = e.getClickedInventory();
            TradeInventory tradeInventory = null;

            if (clickedInventory != null)
            {
                for (TradeInventory inventory : this.tradeInventories) {

                    if (inventory.isInventory(clickedInventory)) {
                        tradeInventory = inventory;
                    }
                }
            }



            if (tradeInventory == null) {

                Inventory inventory = e.getInventory();

                if (inventory != null) {

                    TradeInventory tradeInventoryValue = null;

                    for (TradeInventory tradeInventoryCopy : this.tradeInventories) {

                        if (tradeInventoryCopy.isInventory(inventory)) {
                            tradeInventoryValue = tradeInventoryCopy;
                        }
                    }


                    if (tradeInventoryValue != null &&
                            e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                        e.setCancelled(true);
                        e.setResult(Event.Result.DENY);



                        return;
                    }
                }
            }


            if (tradeInventory != null) {

                final int slotId = e.getRawSlot();

                boolean performed = false;

                if (tradeInventory.isFinished()) {


                    if (tradeInventory.getOtherSlots().contains(Integer.valueOf(slotId))) {

                        if (clickedInventory.getItem(slotId) != null) {

                            if (clickedInventory.getItem(slotId).getType() != Material.AIR) {

                                performed = true;

                                (new BukkitRunnable()
                                {
                                    public void run()
                                    {
                                        TradeInventory tradeInventory = null;

                                        for (TradeInventory inventory : TradeHandler.this.tradeInventories) {

                                            if (inventory.isInventory(clickedInventory)) {
                                                tradeInventory = inventory;
                                            }
                                        }


                                        if (tradeInventory != null &&
                                                clickedInventory.getItem(slotId) != null)
                                        {
                                            if (clickedInventory.getItem(slotId).getType() != Material.AIR)
                                            {
                                                tradeInventory.updateSlots(clickedInventory, tradeInventory.getInventory(clickedInventory));



                                            }



                                        }
                                    }
                                }).runTaskLater((Plugin)Main.getInstance(), 10L);
                            } else {

                                e.setCancelled(true);
                                e.setResult(Event.Result.DENY);
                            }
                        } else {

                            e.setCancelled(true);
                            e.setResult(Event.Result.DENY);
                        }
                    } else {

                        e.setCancelled(true);
                        e.setResult(Event.Result.DENY);

                    }


                }
                else if (tradeInventory.getYouSlots().contains(Integer.valueOf(slotId))) {

                    performed = true;

                    (new BukkitRunnable()
                    {
                        public void run()
                        {
                            TradeInventory tradeInventory = null;

                            for (TradeInventory inventory : TradeHandler.this.tradeInventories) {

                                if (inventory.isInventory(clickedInventory)) {
                                    tradeInventory = inventory;
                                }
                            }


                            if (tradeInventory != null) {
                                tradeInventory.updateSlots(clickedInventory, tradeInventory.getInventory(clickedInventory));

                            }
                        }
                    }).runTaskLater((Plugin)Main.getInstance(), 10L);
                }




                if (slotId == 45 || slotId == 46)
                {
                    tradeInventory.setItem(e.getSlot(), player);
                }


                if (!performed) {

                    e.setCancelled(true);
                    e.setResult(Event.Result.DENY);
                }
            }
        }
    }
}