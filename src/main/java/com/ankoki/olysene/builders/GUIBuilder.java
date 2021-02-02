package com.ankoki.olysene.builders;

import com.ankoki.olysene.utils.Builder;
import com.ankoki.olysene.utils.Utils;
import com.ankoki.olysene.utils.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GUIBuilder extends Builder<Inventory> {
    private final Inventory mainInv;
    private final List<Inventory> pages = new ArrayList<>();
    private Material pageMaterial = null;

    public GUIBuilder(int rows, String... name) {
        Validate.isGreater(rows, 9, "You cannot have an inventory with more than 9 rows!");
        Validate.isLess(rows, 2, "You cannot have an inventory with less than 2 rows!");
        if (name == null) {
            mainInv = Bukkit.createInventory(null, rows, "");
        } else {
            Validate.isLength(name, 1, "You can only put one name!");
            String n = name[0];
            mainInv = Bukkit.createInventory(null, rows, Utils.coloured(n));
        }
    }

    public void setItem(int slot, ItemStack item) {
        mainInv.setItem(slot, item);
    }

    public void addItem(ItemStack item) {
        mainInv.addItem(item);
    }

    public void setPageMaterial(Material material) {
        Validate.notNull(material, "Page material cannot be null!");
        this.pageMaterial = material;
    }

    public void setPage(int page, Inventory inventory) {
        pages.set(page, inventory);
    }

    public void addPage(Inventory inventory) {
        pages.add(inventory);
    }

    public Inventory getPage(int page) {
        Validate.notEmpty(pages, "You haven't got any pages!");
        try {
            return pages.get(page);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("You cannot get a page which doesn't exist!");
        }
    }

    @Override
    protected Inventory build() {
        return mainInv;
    }
}
