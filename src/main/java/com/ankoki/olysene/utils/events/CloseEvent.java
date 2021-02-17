package com.ankoki.olysene.utils.events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public interface CloseEvent {
    void onClick(InventoryCloseEvent e, Inventory inventory, HumanEntity entity);
}
