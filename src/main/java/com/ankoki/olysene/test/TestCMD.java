package com.ankoki.olysene.test;

import com.ankoki.olysene.builders.GUIBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        GUIBuilder builder = new GUIBuilder(6, "&aTest!");
        builder.setBorderSlots(new ItemStack(Material.APPLE));
        builder.addItem(new ItemStack(Material.ANCIENT_DEBRIS));
        builder.setClickEvent((event, inventory, entity, item) -> {
            event.setCancelled(true);
            entity.sendMessage("HIIII!!! THIS WORKED SOMEHOW");
        }, 0, 1, 2, 3, 4, 5, 6, 7, 8);
        builder.setDragEvent((event, inventory, entity, cursor) -> {
            event.setCancelled(true);
            entity.sendMessage("You tried to drag");
        });
        ((Player) sender).openInventory(builder.build());
        sender.sendMessage("shouldve atleast somewhat worked");
        return true;
    }
}