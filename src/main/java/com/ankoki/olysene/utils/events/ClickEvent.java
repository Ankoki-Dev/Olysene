package com.ankoki.olysene.utils.events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface ClickEvent {
    void onClick(Inventory inventory, HumanEntity entity, ItemStack item);
}
