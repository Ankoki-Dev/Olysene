package com.ankoki.olysene.utils.events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface DragEvent {
    void onClick(InventoryDragEvent e, Inventory inventory, HumanEntity entity, ItemStack cursor);
}
