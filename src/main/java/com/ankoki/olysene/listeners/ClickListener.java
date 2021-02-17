package com.ankoki.olysene.listeners;

import com.ankoki.olysene.builders.GUIBuilder;
import com.ankoki.olysene.utils.events.ClickEvent;
import com.ankoki.olysene.utils.events.CloseEvent;
import com.ankoki.olysene.utils.events.DragEvent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
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
                        entry1.getValue().onClick(e, inv, entity, item);
                    }
                }
            }
        }
    }

    @EventHandler
    private void onInventoryDrag(InventoryDragEvent e) {
        Inventory inv = e.getInventory();
        HumanEntity entity = e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        for (Map.Entry<Inventory, DragEvent> entry : GUIBuilder.allDragEvents.entrySet()) {
            if (inv == entry.getKey()) {
                entry.getValue().onClick(e, inv, entity, cursor);
            }
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        HumanEntity entity = e.getPlayer();
        for (Map.Entry<Inventory, CloseEvent> entry : GUIBuilder.allCloseEvents.entrySet()) {
            if (inv == entry.getKey()) {
                entry.getValue().onClick(e, inv, entity);
            }
        }
    }
}
