package com.ankoki.olysene.utils;

import org.bukkit.ChatColor;

public class Utils {

    public static String coloured(String uncoloured) {
        return ChatColor.translateAlternateColorCodes('&', uncoloured);
    }

    public String asString(String[] array, boolean spaces) {
        StringBuilder builder = new StringBuilder();
        for (String value : array) {
            builder.append(value);
            if (spaces) builder.append(" ");
        }
        return builder.toString();
    }
}
