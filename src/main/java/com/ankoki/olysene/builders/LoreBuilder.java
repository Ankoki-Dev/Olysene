package com.ankoki.olysene.builders;

import com.ankoki.olysene.utils.Builder;
import com.ankoki.olysene.utils.Validate;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class LoreBuilder extends Builder<List<String>> {
    List<String> lines = new ArrayList<>();

    public LoreBuilder(String... lines) {
        for (String line : lines) {
            this.lines.add(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    @Override
    protected List<String> build() {
        Validate.notEmpty(lines, "Lore cannot be empty!");
        return lines;
    }
}
