package com.ankoki.olysene.builders;

import com.ankoki.olysene.utils.Builder;
import com.ankoki.olysene.utils.Enchant;
import com.ankoki.olysene.utils.Utils;
import com.ankoki.olysene.utils.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder extends Builder<ItemStack> {
    private final ItemStack item;

    public ItemBuilder(ItemStack item, int stackSize) {
        this.item = item;
        if (item.getType() == Material.SNOWBALL || item.getType() == Material.ENDER_PEARL) {
            Validate.isLess(stackSize, 17, "The max stack size for this item is 16!");
        }
        item.setAmount(stackSize);
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(Material material) {
        Validate.notNull(material, "Material cannot be null!");
        if (isIllegalType(material)) throw new IllegalArgumentException("Material cannot be air or water!");
        this.item = new ItemStack(material);
    }

    public ItemBuilder(Material material, int count) {
        Validate.notNull(material, "Material cannot be null!");
        if (isIllegalType(material)) throw new IllegalArgumentException("Material cannot be air or water!");
        Validate.isPositive(count, "Count cannot equal to or less than 0!");
        this.item = new ItemStack(material, count);
    }

    public ItemBuilder setLore(LoreBuilder loreBuilder) {
        Validate.notNull(loreBuilder, "LoreBuilder cannot be null!");
        ItemMeta meta = item.getItemMeta();
        Validate.notNull(meta, "ItemMeta cannot be null!");
        meta.setLore(loreBuilder.build());
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        Validate.notNull(name, "The new display name cannot be null!");
        ItemMeta meta = item.getItemMeta();
        Validate.notNull(meta, "ItemMeta cannot be null!");
        meta.setDisplayName(Utils.coloured(name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchant enchant, int level) {
        Validate.notNull(enchant, "Enchant cannot be null!");
        ItemMeta meta = item.getItemMeta();
        Validate.notNull(meta, "ItemMeta cannot be null!");
        meta.addEnchant(enchant.getBukkitEnchantment(), level, false);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchant enchant) {
        Validate.notNull(enchant, "Enchant cannot be null!");
        ItemMeta meta = item.getItemMeta();
        Validate.notNull(meta, "ItemMeta cannot be null!");
        meta.addEnchant(enchant.getBukkitEnchantment(), 1, false);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchants(Enchant... enchants) {
        Validate.notNull(enchants, "Enchants cannot be null!");
        ItemMeta meta = item.getItemMeta();
        Validate.notNull(meta, "ItemMeta cannot be null!");
        for (Enchant enchant : enchants) {
            meta.addEnchant(enchant.getBukkitEnchantment(), 1, false);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchants(int level, Enchant... enchants) {
        Validate.notNull(enchants, "Enchants cannot be null!");
        ItemMeta meta = item.getItemMeta();
        Validate.notNull(meta, "ItemMeta cannot be null!");
        for (Enchant enchant : enchants) {
            meta.addEnchant(enchant.getBukkitEnchantment(), level, false);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setType(Material material) {
        if (isIllegalType(material)) throw new IllegalArgumentException("Material cannot be air or water!");
        item.setType(material);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        Validate.notNull(flags, "ItemFlag cannot be null!");
        ItemMeta meta = item.getItemMeta();
        Validate.notNull(meta, "ItemMeta cannot be null!");
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder setGlowing() {
        if (item.getType() == Material.BOW || item.getType() == Material.TRIDENT) {
            this.addEnchant(Enchant.EFFICIENCY);
        } else {
            this.addEnchant(Enchant.INFINITY);
        }
        this.addFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public String getName() {
        return item.getType().toString().toLowerCase().replace("_", " ");
    }

    @Override
    protected ItemStack build() {
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
