package dev.cosmics.pigthingy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MobGui implements Listener {
    private final Player player;
    private final Inventory inventory;
    private boolean settingMob = false;

    public MobGui(Player p) {
        this.player = p;
        inventory = Bukkit.getServer().createInventory(null, 9, "Mob Explosion");
        addItems();
        registerEvents();
    }

    public void openGui() {
        player.openInventory(inventory);
        settingMob = false;
    }

    private void addItems() {
        inventory.setItem(0, createGuiItem(Material.WOOL,"Make Explosion Bigger", 5, "Makes mobs explosion radius bigger"));
        inventory.setItem(1, createGuiItem(Material.WOOL,"Make Explosion Smaller", 14, "Makes mobs explosion radius smaller"));
        inventory.setItem(2, createGuiItem(Material.MONSTER_EGG,"Change Mob", 0, "Change the mob that explodes"));
    }

    protected ItemStack createGuiItem(Material material ,final String name, int data , final String... lore) {
        final ItemStack item = new ItemStack(material, 1, (short) 0, (byte) data);
        final ItemMeta meta = item.getItemMeta();


        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    private void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(this, PigThingy.getPlugin(PigThingy.class));
    }

    private void unregisterEvents() {
        InventoryClickEvent.getHandlerList().unregister(this);
        InventoryCloseEvent.getHandlerList().unregister(this);
        AsyncPlayerChatEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void guiInteractEvent(InventoryClickEvent e) {
        if (!(e.getClickedInventory().getHolder() == null)) {
           return;
        }


        e.setCancelled(true);
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        if (e.getCurrentItem().getType() == Material.WOOL) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Make Explosion Bigger")) {
                MobEvents.addExplosionPower();
                player.sendMessage("Made Explosion bigger");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Make Explosion Smaller")) {
                MobEvents.subtractExplosionPower();
                player.sendMessage("Made Explosion smaller");
            }
        } else if (e.getCurrentItem().getType() == Material.MONSTER_EGG) {
            player.sendMessage("Change mob, type the name of the mob in chat, make sure it is spelled correctly");
            player.closeInventory();
            settingMob = true;
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory() == inventory && !settingMob) {
            unregisterEvents();
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        System.out.println("onChat");
        if (!(e.getPlayer() == player) || !settingMob) {
            return;
        }

        EntityType mob;
        settingMob = false;

        openGui();
        e.setCancelled(true);

        try {
            mob = EntityType.valueOf(e.getMessage().toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException ex) {
            player.sendMessage("Invalid mob");
            return;
        }

        MobEvents.setMob(mob);
        player.sendMessage("Changed mob to " + mob.name());
    }
}
