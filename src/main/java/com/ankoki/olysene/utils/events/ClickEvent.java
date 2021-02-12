package com.ankoki.olysene.utils.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface ClickEvent {
    void onClick(LivingEntity player, ItemStack item);
}
