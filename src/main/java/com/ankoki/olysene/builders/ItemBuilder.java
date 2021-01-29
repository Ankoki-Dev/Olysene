package com.ankoki.olysene.builders;

import com.ankoki.olysene.utils.Enchant;
import com.ankoki.olysene.utils.Utils;
import com.ankoki.olysene.utils.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
    private final ItemStack item;

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(Material material) {
        if (isIllegalType(material)) throw new IllegalArgumentException("Material cannot be air or water!");
        this.item = new ItemStack(material);
    }

    public ItemBuilder(Material material, int count) {
        if (isIllegalType(material)) throw new IllegalArgumentException("Material cannot be air or water!");
        Validate.isPositive(count, "Count cannot equal to or less than 0!");
        this.item = new ItemStack(material, count);
    }

    public ItemBuilder setLore(LoreBuilder loreBuilder) {
        ItemMeta meta = item.getItemMeta();
        Validate.notNull(meta, "ItemMeta cannot be null!");
        meta.setLore(loreBuilder.build());
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        ItemMeta meta = item.getItemMeta();
        Validate.notNull(meta, "ItemMeta cannot be null!");
        meta.setDisplayName(Utils.coloured(name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchant enchant, int level) {
        ItemMeta meta = item.getItemMeta();
        Validate.notNull(meta, "ItemMeta cannot be null!");
        meta.addEnchant(enchant.getBukkitEnchantment(), level, false);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setType(Material material) {
        if (isIllegalType(material)) throw new IllegalArgumentException("Material cannot be air or water!");
        item.setType(material);
        return this;
    }

    public ItemStack build() {
        return item;
    }

    private boolean isIllegalType(Material... materials) {
        for (Material mat : materials) {
            if (mat == Material.AIR) return true;
            if (mat == Material.CAVE_AIR) return true;
            if (mat == Material.VOID_AIR) return true;
            if (mat == Material.WATER) return true;
        }
        return false;
    }
}
