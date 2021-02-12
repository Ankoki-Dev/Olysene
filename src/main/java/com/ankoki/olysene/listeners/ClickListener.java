package com.ankoki.olysene.listeners;

import com.ankoki.olysene.builders.GUIBuilder;
import com.ankoki.olysene.utils.events.ClickEvent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ClickListener implements Listener {

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        HumanEntity entity = e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        int slot = e.getSlot();
        for (Map.Entry<Inventory, Map<Integer, ClickEvent>> entry : GUIBuilder.allClickEvents.entrySet()) {
            if (inv == entry.getKey()) {
                for (Map.Entry<Integer, ClickEvent> entry1 : entry.getValue().entrySet()) {
                    if (slot == entry1.getKey()) {
                        entry1.getValue().onClick(entity, item);
                    }
                }
            }
        }
    }
}
