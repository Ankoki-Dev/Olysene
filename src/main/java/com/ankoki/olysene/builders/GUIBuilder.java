package com.ankoki.olysene.builders;

import com.ankoki.olysene.utils.Builder;
import com.ankoki.olysene.utils.Utils;
import com.ankoki.olysene.utils.Validate;
import com.ankoki.olysene.utils.events.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GUIBuilder extends Builder<Inventory> {
    private final Inventory mainInv;
    private final List<Inventory> pages = new ArrayList<>();
    public static Map<Inventory, Map<Integer, ClickEvent>> allClickEvents = new HashMap<>();
    private Material pageMaterial = null;

    public GUIBuilder(int rows, String name) {
        Validate.isGreater(rows, 9, "You cannot have an inventory with more than 9 rows!");
        Validate.isLess(rows, 2, "You cannot have an inventory with less than 2 rows!");
        mainInv = Bukkit.createInventory(null, rows, Utils.coloured(name));
    }

    public GUIBuilder(int rows, String name, InventoryType type) {
        Validate.isGreater(rows, 9, "You cannot have an inventory with more than 9 rows!");
        Validate.isLess(rows, 2, "You cannot have an inventory with less than 2 rows!");
        mainInv = Bukkit.createInventory(null, type, Utils.coloured(name));
    }

    public GUIBuilder setItem(int slot, ItemStack item) {
        mainInv.setItem(slot, item);
        return this;
    }

    public GUIBuilder addItem(ItemStack item) {
        mainInv.addItem(item);
        return this;
    }

    public GUIBuilder setPageMaterial(Material material) {
        Validate.notNull(material, "Page material cannot be null!");
        this.pageMaterial = material;
        return this;
    }

    public GUIBuilder setPage(int page, Inventory inventory) {
        pages.set(page, inventory);
        return this;
    }

    public GUIBuilder addPage(Inventory inventory) {
        pages.add(inventory);
        return this;
    }

    public Inventory getPage(int page) {
        Validate.notEmpty(pages, "You haven't got any pages!");
        try {
            return pages.get(page);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("You cannot get a page which doesn't exist!");
        }
    }

    public GUIBuilder setBorderSlots(ItemStack item) {
        for (int slot : getBorderSlots(this.mainInv)) {
            mainInv.setItem(slot, item);
        }
        return this;
    }

    public GUIBuilder setClickEvent(ClickEvent event, int... slots) {
        Map<Integer, ClickEvent> map = allClickEvents.get(mainInv) == null ? new HashMap<>() : allClickEvents.get(mainInv);
        for (int i : slots) {
            map.put(i, event);
        }
        allClickEvents.put(mainInv, map);
        return this;
    }

    private Integer[] getBorderSlots(Inventory inv) {
        List<Integer> slotsList = new ArrayList<>();
        InventoryType type = inv.getType();
        int rows = 0;
        if (type == InventoryType.CHEST ||
                type == InventoryType.ENDER_CHEST ||
                type == InventoryType.SHULKER_BOX ||
                type == InventoryType.BARREL) rows = inv.getSize() / 9;
        if (type == InventoryType.DISPENSER ||
                type == InventoryType.DROPPER) rows = 3;
        if (type == InventoryType.HOPPER) return new Integer[]{0, 4};
        if (rows == 0) return new Integer[]{};
        int slots = inv.getSize();
        int slotsPerRow = slots / rows;
        for (int i = 1; i <= rows; i++) {
            slotsList.add(slotsPerRow * (i - 1));
            slotsList.add((slotsPerRow * (i - 1)) + (slotsPerRow - 1));
        }
        for (int i = 1; i <= (slotsPerRow - 2); i++) {
            slotsList.add(i);
        }
        for (int i = slots - 2; i <= ((rows - 1) * slotsPerRow) + 1; i++) {
            slotsList.add(i);
        }
        for (int i = (inv.getSize() - 1); i >= (inv.getSize() - slotsPerRow); i--) {
            slotsList.add(i);
        }
        return slotsList.toArray(new Integer[0]);
    }

    @Override
    protected Inventory build() {
        return mainInv;
    }
}
